package net.skeletoncrew.bonezone;

import com.mojang.datafixers.kinds.Const;
import net.darkhax.bookshelf.api.Services;
import net.darkhax.bookshelf.api.registry.RegistryDataProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.skeletoncrew.bonezone.block.BoneCarverBlock;
import net.skeletoncrew.bonezone.block.BoneLadderBlock;
import net.skeletoncrew.bonezone.block.CustomPotBlock;
import net.skeletoncrew.bonezone.block.SpineSkullBlock;
import net.skeletoncrew.bonezone.recipe.bonecarving.BonecarvingRecipeSerializer;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenu;

import java.util.IdentityHashMap;
import java.util.Map;

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
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_goat");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_deer");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_bird");
        this.createPotsFor("skeleton", Constants.SKELETON_POT_TYPES);
        this.createPotsFor("wither", Constants.WITHER_POT_TYPES);
        this.createPotsFor("creeper", Constants.CREEPER_POT_TYPES);

        // Items
        // TODO Only if we have non-block items

        // Menus
        this.menus.add(() -> Services.CONSTRUCTS.menuType(BonecarverMenu::fromNetwork), "bonecarver");
    }

    private void createPotsFor(String type, Map<Item, CustomPotBlock> variants) {

        final BlockBehaviour.Properties potProps = BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.BONE_BLOCK).instabreak().noOcclusion();

        this.blocks.add(() -> new CustomPotBlock(potProps, Items.AIR, variants), "empty_" + type + "_pot");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.OAK_SAPLING, variants), type + "_potted_oak_sapling");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.SPRUCE_SAPLING, variants), type + "_potted_spruce_sapling");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BIRCH_SAPLING, variants), type + "_potted_birch_sapling");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.JUNGLE_SAPLING, variants), type + "_potted_jungle_sapling");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.ACACIA_SAPLING, variants), type + "_potted_acacia_sapling");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.DARK_OAK_SAPLING, variants), type + "_potted_dark_oak_sapling");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.MANGROVE_PROPAGULE, variants), type + "_potted_mangrove_propagule");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.FERN, variants), type + "_potted_fern");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.DANDELION, variants), type + "_potted_dandelion");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.POPPY, variants), type + "_potted_poppy");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BLUE_ORCHID, variants), type + "_potted_blue_orchid");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.ALLIUM, variants), type + "_potted_allium");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.AZURE_BLUET, variants), type + "_potted_azure_bluet");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.RED_TULIP, variants), type + "_potted_red_tulip");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.ORANGE_TULIP, variants), type + "_potted_orange_tulip");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WHITE_TULIP, variants), type + "_potted_white_tulip");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.PINK_TULIP, variants), type + "_potted_pink_tulip");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.OXEYE_DAISY, variants), type + "_potted_oxeye_daisy");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CORNFLOWER, variants), type + "_potted_cornflower");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.LILY_OF_THE_VALLEY, variants), type + "_potted_lily_of_the_valley");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WITHER_ROSE, variants), type + "_potted_wither_rose");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.RED_MUSHROOM, variants), type + "_potted_red_mushroom");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BROWN_MUSHROOM, variants), type + "_potted_brown_mushroom");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.DEAD_BUSH, variants), type + "_potted_dead_bush");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CACTUS, variants), type + "_potted_cactus");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BAMBOO, variants), type + "_potted_bamboo");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CRIMSON_FUNGUS, variants), type + "_potted_crimson_fungus");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WARPED_FUNGUS, variants), type + "_potted_warped_fungus");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CRIMSON_ROOTS, variants), type + "_potted_crimson_roots");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WARPED_ROOTS, variants), type + "_potted_warped_roots");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.AZALEA, variants), type + "_potted_azalea");
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.FLOWERING_AZALEA, variants), type + "_potted_flowering_azalea");
    }
}