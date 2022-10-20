package net.skeletoncrew.bonezone.block;

import net.darkhax.bookshelf.api.block.IBindRenderLayer;
import net.darkhax.bookshelf.api.block.IItemBlockProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

public class CustomPotBlock extends Block implements IBindRenderLayer, IItemBlockProvider, SimpleWaterloggedBlock {

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

    private final Map<Item, CustomPotBlock> variants;
    private final Item contained;

    public CustomPotBlock(Properties properties, Item contained, Map<Item, CustomPotBlock> variants) {

        super(properties);
        this.contained = contained;
        this.variants = variants;
        this.variants.put(contained, this);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public RenderType getRenderLayerToBind() {

        return RenderType.cutout();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {

        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldLevel, BlockPos pos, Player player, InteractionHand hand, BlockHitResult clickContext) {

        final ItemStack heldStack = player.getItemInHand(hand);

        // Take flower out
        if (heldStack.isEmpty() && !this.isEmpty()) {

            if (!player.getAbilities().instabuild) {
                final ItemStack giveStack = new ItemStack(this.contained);
                player.getInventory().add(giveStack);
            }
            worldLevel.setBlock(pos, this.getEmptyBlock().applyFrom(state), 3);
            worldLevel.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        // Put flower in
        else if (this.isEmpty()) {

            final CustomPotBlock toPlace = this.variants.getOrDefault(heldStack.getItem(), this);

            if (toPlace != this && !toPlace.isEmpty()) {

                worldLevel.setBlock(pos, toPlace.applyFrom(state), 3);
                player.awardStat(Stats.POT_FLOWER);
                worldLevel.playSound(player, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!player.getAbilities().instabuild) {

                    heldStack.shrink(1);
                }
            }
        }

        return InteractionResult.CONSUME;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        FluidState existingFluidState = context.getLevel().getFluidState(context.getClickedPos());

        for (Direction lookingDirection : context.getNearestLookingDirections()) {

            if (lookingDirection.getAxis().isHorizontal()) {

                BlockState placedState = this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, lookingDirection.getOpposite());
                return placedState.setValue(BlockStateProperties.WATERLOGGED, existingFluidState.getType() == Fluids.WATER);
            }
        }

        return null;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {

        return this.isEmpty() ? super.getCloneItemStack(world, pos, state) : new ItemStack(this.contained);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {

        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {

        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {

        stateBuilder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasItemBlock(Block block) {

        // While we could register items for each pot, vanilla only has one item for the empty pot with dirt.
        return block instanceof CustomPotBlock pot && pot.isEmpty();
    }

    public Item getContainedItem() {

        return this.contained;
    }

    /**
     * Creates a blockstate of the current block that matches the state of an existing blockstate. For example when
     * planting a flower in the pot we change block but copy the waterlogged and rotation.
     *
     * @param oldState The old state to copy our new properties from.
     * @return The resulting blockstate.
     */
    private BlockState applyFrom(BlockState oldState) {

        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, oldState.getValue(BlockStateProperties.WATERLOGGED)).setValue(BlockStateProperties.HORIZONTAL_FACING, oldState.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    /**
     * Gets the empty pot variant of the current pot type.
     *
     * @return The empty variant of the current pot type.
     */
    public CustomPotBlock getEmptyBlock() {

        return this.variants.get(Items.AIR);
    }

    /**
     * Checks if the pot block is the empty variant.
     *
     * @return If the pot is empty.
     */
    public boolean isEmpty() {

        return this.contained == Items.AIR;
    }
}