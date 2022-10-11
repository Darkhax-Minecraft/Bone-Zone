package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.bookshelf.api.registry.RegistryDataProvider;
import net.minecraft.world.item.Items;
import net.skeletoncrew.bonezone.block.BoneCarverBlock;
import net.skeletoncrew.bonezone.block.BoneLadderBlock;
import net.skeletoncrew.bonezone.block.SpineSkullBlock;
import net.skeletoncrew.bonezone.block.TempBlock;
import net.skeletoncrew.bonezone.recipe.bonecarving.BonecarvingRecipeSerializer;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenu;

public class Content extends RegistryDataProvider {

    public Content() {

        super(Constants.MOD_ID);

        this.withCreativeTab(() -> Items.SKELETON_SKULL);
        this.withAutoItemBlocks();
        this.bindBlockRenderLayers();

        // Recipe Types
        this.recipeTypes.add("bonecarving");

        // Recipe Serializers
        this.recipeSerializers.add(BonecarvingRecipeSerializer::new, "bonecarving");

        // Blocks
        this.blocks.add(BoneLadderBlock::new, "bone_ladder");
        this.blocks.add(BoneCarverBlock::new, "bonecarver");
        this.blocks.add(TempBlock::new, "creeper_skull");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_goat");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_deer");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_bird");

        // Items
        // TODO Only if we have non-block items

        // Menus
        this.menus.add(() -> Services.CONSTRUCTS.menuType(BonecarverMenu::fromNetwork), "bonecarver");
    }
}