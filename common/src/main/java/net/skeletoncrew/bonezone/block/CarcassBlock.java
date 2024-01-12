package net.skeletoncrew.bonezone.block;

import com.mojang.serialization.MapCodec;
import net.darkhax.bookshelf.api.block.IBindRenderLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CarcassBlock extends HorizontalDirectionalBlock implements IBindRenderLayer {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    private static final BlockBehaviour.Properties props = BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).sound(SoundType.BONE_BLOCK).noOcclusion().strength(1.5f);
    protected static final VoxelShape BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public CarcassBlock() {
        super(props);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HALF, Half.TOP).setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var dir = context.getHorizontalDirection();
        var clk = context.getClickedPos();
        var pos = clk.relative(dir);
        var lev = context.getLevel();
        return lev.getBlockState(pos).canBeReplaced(context) && lev.getWorldBorder().isWithinBounds(pos) ? this.defaultBlockState().setValue(FACING, dir) : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return BASE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(FACING, HALF);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack stack) {
        super.setPlacedBy(level, pos, state, livingEntity, stack);
        if (!level.isClientSide) {
            BlockPos nyew = pos.relative(state.getValue(FACING));
            level.setBlock(nyew, state.setValue(HALF, Half.BOTTOM), 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    @Override
    public boolean isPathfindable(BlockState level, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState nyewState, LevelAccessor levelAccessor, BlockPos pos, BlockPos nyewPos) {
        if (dir == getNeighbourDirection(state.getValue(HALF), state.getValue(FACING)) && !nyewState.is(this)) {
            return Blocks.AIR.defaultBlockState();
        }
        else {
            return super.updateShape(state, dir, nyewState, levelAccessor, pos, nyewPos);
        }
    }

    private static Direction getNeighbourDirection(Half half, Direction dir) {
        return half == Half.TOP ? dir : dir.getOpposite();
    }

    @Override
    public RenderType getRenderLayerToBind() {
        return RenderType.cutout();
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        throw new RuntimeException("This block has not implemented codecs yet. Sorry :(");
    }
}
