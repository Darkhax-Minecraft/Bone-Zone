package net.skeletoncrew.bonezone.block;

import net.darkhax.bookshelf.api.block.IBindRenderLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Skullpot extends Block implements IBindRenderLayer {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    private static final Properties PROPERTIES = Properties.of(Material.DECORATION)
            .noOcclusion()
            .strength(0.4f)
            .sound(SoundType.BONE_BLOCK)
            .isValidSpawn(Skullpot::never)
            .isRedstoneConductor(Skullpot::never)
            .isSuffocating(Skullpot::never)
            .isViewBlocking(Skullpot::never);

    public Skullpot() {
        super(PROPERTIES);
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return false;
    }

    @Override
    public RenderType getRenderLayerToBind() {
        return RenderType.cutout();
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return SHAPE;
    }

}
