package net.skeletoncrew.bonezone.block;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.darkhax.bookshelf.api.block.IBindRenderLayer;
import net.darkhax.bookshelf.api.block.IItemBlockProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class CandleSkullBlock extends AbstractCandleBlock implements IBindRenderLayer, IItemBlockProvider {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SKULL = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    protected static final VoxelShape CANDLE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
    private static final VoxelShape BOTH = Shapes.or(SKULL, CANDLE);
    private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

    private final Map<Item, CandleSkullBlock> variants;
    private final Item contained;
    private final MapCodec<? extends AbstractCandleBlock> codec;

    public CandleSkullBlock(BlockBehaviour.Properties props, Item candle, Map<Item, CandleSkullBlock> variants) {

        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
        variants.put(candle, this);
        this.variants = variants;
        this.contained = candle;
        this.codec = BlockBehaviour.simpleCodec(newProps -> new CandleSkullBlock(newProps, candle, variants));
    }

    @Override
    protected MapCodec<? extends AbstractCandleBlock> codec() {
        
        return this.codec;
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        final ItemStack heldStack = player.getItemInHand(hand);

        // Attempt to place a candle if one does not exist.
        if (this.hasNoCandle()) {

            final CandleSkullBlock toPlace = this.variants.getOrDefault(heldStack.getItem(), this);

            if (toPlace != this && !toPlace.hasNoCandle()) {

                level.setBlock(pos, toPlace.applyFrom(state), 3);
                level.playSound(player, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!player.getAbilities().instabuild) {

                    heldStack.shrink(1);
                }
            }
        }

        // Check if the candle is clicked on
        else if (candleHit(hitResult)) {

            // Light the candle
            if (!state.getValue(LIT) && (heldStack.is(Items.FLINT_AND_STEEL) || heldStack.is(Items.FIRE_CHARGE))) {

                level.setBlock(pos, state.setValue(LIT, true), 11);
            }

            else if (player.getItemInHand(hand).isEmpty()) {

                // Extinguish if lit
                if (state.getValue(LIT)) {

                    extinguish(player, state, level, pos);
                }

                // Remove candle if unlit
                else {

                    if (!player.getAbilities().instabuild) {

                        final ItemStack giveStack = new ItemStack(this.contained);
                        player.getInventory().add(giveStack);
                    }

                    level.playSound(player, pos, SoundEvents.CANDLE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pos, this.getEmptyBlock().applyFrom(state), 3);
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {

        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {

        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {

        return hasNoCandle() ? SKULL : BOTH;
    }

    public boolean hasNoCandle() {

        return Items.AIR.equals(contained);
    }

    private boolean candleHit(BlockHitResult hitResult) {

        return hitResult.getLocation().y - (double) hitResult.getBlockPos().getY() > 0.5D;
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {

        return PARTICLE_OFFSETS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        for (Direction lookingDirection : context.getNearestLookingDirections()) {

            if (lookingDirection.getAxis().isHorizontal()) {

                BlockState placedState = this.defaultBlockState().setValue(FACING, lookingDirection.getOpposite());
                return placedState.setValue(LIT, false);
            }
        }

        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> define) {
        define.add(FACING, LIT);
    }

    @Override
    public RenderType getRenderLayerToBind() {
        return RenderType.cutout();
    }

    @Override
    public boolean hasItemBlock(Block block) {

        // While we could register items for each pot, vanilla only has one item for the empty pot with dirt.
        return block instanceof CandleSkullBlock skull && skull.hasNoCandle();
    }

    private BlockState applyFrom(BlockState oldState) {

        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, oldState.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    public CandleSkullBlock getEmptyBlock() {

        return this.variants.get(Items.AIR);
    }
}
