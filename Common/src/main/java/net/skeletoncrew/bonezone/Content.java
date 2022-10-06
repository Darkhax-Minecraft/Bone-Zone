package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.registry.RegistryDataProvider;
import net.minecraft.world.item.Items;
import net.skeletoncrew.bonezone.block.BoneLadderBlock;

public class Content extends RegistryDataProvider {

    public Content() {

        super(Constants.MOD_ID);

        this.withCreativeTab(() -> Items.SKELETON_SKULL);
        this.withAutoItemBlocks();
        this.bindBlockRenderLayers();

        // Recipe Types
        this.recipeTypes.add("bonecarving");

        // Recipe Serializers
        // TODO

        // Blocks
        this.blocks.add(BoneLadderBlock::new, "bone_ladder");

        // Items
        // TODO

        // Menus
        // TODO
    }
}