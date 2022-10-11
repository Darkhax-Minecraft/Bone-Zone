package net.skeletoncrew.bonezone.block;

import net.darkhax.bookshelf.api.block.IBindRenderLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpineSkullBlock extends Block implements IBindRenderLayer, Wearable {

    // TODO The ItemBlock is probably enchantable which we don't want
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

    private static final Properties PROPERTIES = Properties.of(Material.DECORATION)
            .noOcclusion()
            .strength(0.4f)
            .sound(SoundType.BONE_BLOCK)
            .isValidSpawn(SpineSkullBlock::never)
            .isRedstoneConductor(SpineSkullBlock::never)
            .isSuffocating(SpineSkullBlock::never)
            .isViewBlocking(SpineSkullBlock::never);

    public SpineSkullBlock() {
        super(PROPERTIES);
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return false;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderType getRenderLayerToBind() {

        return RenderType.cutout();
    }


}
