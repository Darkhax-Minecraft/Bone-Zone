package net.skeletoncrew.bonezone.block;

import net.darkhax.bookshelf.api.block.IBindRenderLayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BoneLadderBlock extends LadderBlock implements IBindRenderLayer {

    private static final BlockBehaviour.Properties PROPERTIES = BlockBehaviour.Properties.of(Material.DECORATION).strength(0.4F).sound(SoundType.BONE_BLOCK).noOcclusion();

    public BoneLadderBlock() {

        super(PROPERTIES);
    }

    @Override
    public RenderType getRenderLayerToBind() {

        return RenderType.cutout();
    }
}
