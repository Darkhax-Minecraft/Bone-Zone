package net.skeletoncrew.bonezone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.skeletoncrew.bonezone.Constants;
import net.skeletoncrew.bonezone.recipe.mobsanding.AbstractMobsandingRecipe;
import net.skeletoncrew.bonezone.recipe.mobsanding.MobsandingRecipe;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenuProvider;

public class BoneCarverBlock extends Block {

    private static final ResourceKey<DamageType> SANDING_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Constants.MOD_ID, "sanding"));

    private static final Properties PROPERTIES = Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F);
    private static final Component TITLE = Component.translatable("container.bonezone.bonecarver");
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public BoneCarverBlock() {

        super(PROPERTIES);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
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

        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorAxis) {

        return state.rotate(mirrorAxis.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {

        stateBuilder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldLevel, BlockPos pos, PathComputationType type) {

        return false;
    }

    @Override
    public void stepOn(Level worldLevel, BlockPos pos, BlockState state, Entity entity) {

        if (entity instanceof LivingEntity living && worldLevel.getGameTime() % 20 == 1) {

            final AbstractMobsandingRecipe recipe = MobsandingRecipe.findRecipe(worldLevel, pos,entity);

            entity.hurt(getSource(worldLevel, SANDING_DAMAGE), recipe == null ? 2f : 3f);

            if (recipe != null) {

                recipe.onCrafted(worldLevel, pos, entity);
            }
        }
    }

    private static DamageSource getSource(Level level, ResourceKey<DamageType> id) {

        final Registry<DamageType> registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        final Holder.Reference<DamageType> damage = registry.getHolderOrThrow(id);
        return new DamageSource(damage);
    }
}