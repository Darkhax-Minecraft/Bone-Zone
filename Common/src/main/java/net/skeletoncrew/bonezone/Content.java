package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.bookshelf.api.registry.RegistryDataProvider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.skeletoncrew.bonezone.block.BasicBoneBlock;
import net.skeletoncrew.bonezone.block.BoneCarverBlock;
import net.skeletoncrew.bonezone.block.BoneLadderBlock;
import net.skeletoncrew.bonezone.block.CandleSkullBlock;
import net.skeletoncrew.bonezone.block.CarcassBlock;
import net.skeletoncrew.bonezone.block.CustomPotBlock;
import net.skeletoncrew.bonezone.block.SpineSkullBlock;
import net.skeletoncrew.bonezone.recipe.bonecarving.BonecarvingRecipeSerializer;
import net.skeletoncrew.bonezone.recipe.mobsanding.MobsandingRecipeSerializer;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenu;

import java.util.Map;
import java.util.function.ToIntFunction;

public class Content extends RegistryDataProvider {

    public Content() {

        super(Constants.MOD_ID);

//        new CandleSkullGen("skeleton", "minecraft:entity/skeleton/skeleton").genBlockStates().genModels().genItemFile();
//        new CandleSkullGen("wither", "minecraft:entity/skeleton/wither_skeleton").genBlockStates().genModels().genItemFile();
//        new CandleSkullGen("stray", "minecraft:entity/skeleton/stray").genBlockStates().genModels().genItemFile();

        this.withCreativeTab(() -> Items.SKELETON_SKULL);
        this.withAutoItemBlocks();
        this.bindBlockRenderLayers();

        // Recipe Types
        this.recipeTypes.add("bonecarving");
        this.recipeTypes.add("mobsanding");

        // Recipe Serializers
        this.recipeSerializers.add(BonecarvingRecipeSerializer::new, "bonecarving");
        this.recipeSerializers.add(MobsandingRecipeSerializer::new, "mobsanding");

        // Blocks
        this.blocks.add(BoneCarverBlock::new, "bonecarver");

        /// Bone
        this.blocks.add(BasicBoneBlock::bone, "bone_bricks");
        this.blocks.add(BasicBoneBlock::bone, "bone_mosaic");
        this.blocks.add(BoneLadderBlock::new, "bone_ladder");
        this.createPotsFor("skeleton", Constants.SKELETON_POT_TYPES, false);
        this.createPotsFor("skeleton", Constants.FLIPPED_SKELETON_POT_TYPES, true);
        this.createCandleSkull("skeleton", Constants.CANDLE_SKELETON_TYPES);

        /// Wither
        this.blocks.add(BasicBoneBlock::wither, "wither_bone_bricks");
        this.blocks.add(BasicBoneBlock::wither, "wither_bone_mosaic");
        this.blocks.add(BoneLadderBlock::new, "wither_bone_ladder");
        this.createPotsFor("wither", Constants.WITHER_POT_TYPES, false);
        this.createPotsFor("wither", Constants.FLIPPED_WITHER_POT_TYPES, true);
        this.createCandleSkull("wither", Constants.CANDLE_WITHER_TYPES);

        /// Stray
        this.blocks.add(BasicBoneBlock::stray, "stray_bone_bricks");
        this.blocks.add(BasicBoneBlock::stray, "stray_bone_mosaic");
        this.blocks.add(BoneLadderBlock::new, "stray_bone_ladder");
        this.createPotsFor("stray", Constants.STRAY_POT_TYPES, false);
        this.createPotsFor("stray", Constants.FLIPPED_STRAY_POT_TYPES, true);
        this.createCandleSkull("stray", Constants.CANDLE_STRAY_TYPES);

        /// Misc
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_goat");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_deer");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_bird");
        this.blocks.add(SpineSkullBlock::new, "spinal_skull_bull");
        this.blocks.add(CarcassBlock::new, "carcass");
        this.createPotsFor("creeper", Constants.CREEPER_POT_TYPES, false);
        this.createPotsFor("creeper", Constants.FLIPPED_CREEPER_POT_TYPES, true);

        // Items
        // TODO Only if we have non-block items

        // Menus
        this.menus.add(() -> Services.CONSTRUCTS.menuType(BonecarverMenu::fromNetwork), "bonecarver");
    }

    private void createCandleSkull(String type, Map<Item, CandleSkullBlock> map) {

        final BlockBehaviour.Properties props = BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.BONE_BLOCK).strength(0.1f).noOcclusion().lightLevel(litBlockEmission(6));

        var nameBase = "candle_skull_" + type + "_";

        for (DyeColor color : DyeColor.values()) {

            this.blocks.add(() -> new CandleSkullBlock(props, Constants.CANDLES.get(color), map), nameBase + color.getName());
        }

        this.blocks.add(() -> new CandleSkullBlock(props, Items.CANDLE, map), nameBase + "regular");
        this.blocks.add(() -> new CandleSkullBlock(props, Items.AIR, map), nameBase + "empty");

    }

    private static ToIntFunction<BlockState> litBlockEmission(int level) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? level : 0;
    }

    private void createPotsFor(String type, Map<Item, CustomPotBlock> variants, boolean flipped) {
        String flip = flipped ? "_flipped" : "";
        final BlockBehaviour.Properties potProps = BlockBehaviour.Properties.of(Material.DECORATION).sound(SoundType.BONE_BLOCK).instabreak().noOcclusion();

        this.blocks.add(() -> new CustomPotBlock(potProps, Items.AIR, variants), "empty_" + type + "_pot" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.OAK_SAPLING, variants), type + "_potted_oak_sapling" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.SPRUCE_SAPLING, variants), type + "_potted_spruce_sapling" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BIRCH_SAPLING, variants), type + "_potted_birch_sapling" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.JUNGLE_SAPLING, variants), type + "_potted_jungle_sapling" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.ACACIA_SAPLING, variants), type + "_potted_acacia_sapling" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.DARK_OAK_SAPLING, variants), type + "_potted_dark_oak_sapling" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.MANGROVE_PROPAGULE, variants), type + "_potted_mangrove_propagule" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.FERN, variants), type + "_potted_fern" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.DANDELION, variants), type + "_potted_dandelion" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.POPPY, variants), type + "_potted_poppy" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BLUE_ORCHID, variants), type + "_potted_blue_orchid" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.ALLIUM, variants), type + "_potted_allium" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.AZURE_BLUET, variants), type + "_potted_azure_bluet" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.RED_TULIP, variants), type + "_potted_red_tulip" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.ORANGE_TULIP, variants), type + "_potted_orange_tulip" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WHITE_TULIP, variants), type + "_potted_white_tulip" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.PINK_TULIP, variants), type + "_potted_pink_tulip" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.OXEYE_DAISY, variants), type + "_potted_oxeye_daisy" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CORNFLOWER, variants), type + "_potted_cornflower" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.LILY_OF_THE_VALLEY, variants), type + "_potted_lily_of_the_valley" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WITHER_ROSE, variants), type + "_potted_wither_rose" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.RED_MUSHROOM, variants), type + "_potted_red_mushroom" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BROWN_MUSHROOM, variants), type + "_potted_brown_mushroom" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.DEAD_BUSH, variants), type + "_potted_dead_bush" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CACTUS, variants), type + "_potted_cactus" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.BAMBOO, variants), type + "_potted_bamboo" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CRIMSON_FUNGUS, variants), type + "_potted_crimson_fungus" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WARPED_FUNGUS, variants), type + "_potted_warped_fungus" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.CRIMSON_ROOTS, variants), type + "_potted_crimson_roots" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.WARPED_ROOTS, variants), type + "_potted_warped_roots" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.AZALEA, variants), type + "_potted_azalea" + flip);
        this.blocks.add(() -> new CustomPotBlock(potProps, Items.FLOWERING_AZALEA, variants), type + "_potted_flowering_azalea" + flip);
    }
}