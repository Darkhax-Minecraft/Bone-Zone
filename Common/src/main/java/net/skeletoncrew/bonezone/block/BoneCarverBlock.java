package net.skeletoncrew.bonezone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenuProvider;

public class BoneCarverBlock extends Block {

    private static final Properties PROPERTIES = Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F);
    private static final Component TITLE = Component.translatable("container.bonezone.bonecarver");
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    public BoneCarverBlock() {

        super(PROPERTIES);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level worldLevel, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitData) {

        if (worldLevel.isClientSide) {

            return InteractionResult.SUCCESS;
        }

        else if (player instanceof ServerPlayer serverPlayer) {

            //player.awardStat(Stats.INTERACT_WITH_);
            BonecarverMenuProvider.openMenu(serverPlayer, pos, TITLE);
            return InteractionResult.CONSUME;
        }

        return super.use(state, worldLevel, pos, player, hand, hitData);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldLevel, BlockPos pos, CollisionContext context) {

        return SHAPE;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {

        return true;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {

        return state.setValue(BlockStateProperties.FACING, rotation.rotate(state.getValue(BlockStateProperties.FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorAxis) {

        return state.rotate(mirrorAxis.getRotation(state.getValue(BlockStateProperties.FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {

        stateBuilder.add(BlockStateProperties.FACING);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldLevel, BlockPos pos, PathComputationType type) {

        return false;
    }
}