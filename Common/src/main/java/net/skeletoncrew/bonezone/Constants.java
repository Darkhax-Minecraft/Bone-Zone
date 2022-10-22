package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.skeletoncrew.bonezone.block.CandleSkullBlock;
import net.skeletoncrew.bonezone.block.CustomPotBlock;
import net.skeletoncrew.bonezone.block.SpineSkullBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

public class Constants {

    public static final String MOD_ID = "bonezone";
    public static final String MOD_NAME = "Bone Zone";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();

    // Pot Flower Registries (exposed for mod support)
    public static final Map<Item, CustomPotBlock> SKELETON_POT_TYPES = new IdentityHashMap<>();
    public static final Map<Item, CustomPotBlock> WITHER_POT_TYPES = new IdentityHashMap<>();
    public static final Map<Item, CustomPotBlock> CREEPER_POT_TYPES = new IdentityHashMap<>();
    public static final Map<Item, CustomPotBlock> STRAY_POT_TYPES = new IdentityHashMap<>();

    public static final Map<Item, CustomPotBlock> FLIPPED_SKELETON_POT_TYPES = new IdentityHashMap<>();
    public static final Map<Item, CustomPotBlock> FLIPPED_WITHER_POT_TYPES = new IdentityHashMap<>();
    public static final Map<Item, CustomPotBlock> FLIPPED_CREEPER_POT_TYPES = new IdentityHashMap<>();
    public static final Map<Item, CustomPotBlock> FLIPPED_STRAY_POT_TYPES = new IdentityHashMap<>();

    public static final Map<Item, CandleSkullBlock> CANDLE_SKULL_TYPES = new IdentityHashMap<>();

    // Menus
    public static final CachedSupplier<MenuType<?>> BONECARVER_MENU = CachedSupplier.cache(() -> Registry.MENU.get(new ResourceLocation(Constants.MOD_ID, "bonecarver")));

    // Biomecolored plant pots
    public static final CachedSupplier<Block> SKELETON_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "skeleton_potted_fern")));
    public static final CachedSupplier<Block> WITHER_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "wither_potted_fern")));
    public static final CachedSupplier<Block> CREEPER_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "creeper_potted_fern")));
    public static final CachedSupplier<Block> STRAY_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "stray_potted_fern")));
    public static final CachedSupplier<Block> FLIPPED_SKELETON_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "skeleton_potted_fern_flipped")));
    public static final CachedSupplier<Block> FLIPPED_WITHER_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "wither_potted_fern_flipped")));
    public static final CachedSupplier<Block> FLIPPED_CREEPER_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "creeper_potted_fern_flipped")));
    public static final CachedSupplier<Block> FLIPPED_STRAY_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "stray_potted_fern_flipped")));

    public static final Map<DyeColor, Item> CANDLES = new IdentityHashMap<>();

    static {
        CANDLES.put(DyeColor.BLACK, Items.BLACK_CANDLE);
        CANDLES.put(DyeColor.BLUE, Items.BLUE_CANDLE);
        CANDLES.put(DyeColor.BROWN, Items.BROWN_CANDLE);
        CANDLES.put(DyeColor.CYAN, Items.CYAN_CANDLE);
        CANDLES.put(DyeColor.GRAY, Items.GRAY_CANDLE);
        CANDLES.put(DyeColor.GREEN, Items.GREEN_CANDLE);
        CANDLES.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_CANDLE);
        CANDLES.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_CANDLE);
        CANDLES.put(DyeColor.LIME, Items.LIME_CANDLE);
        CANDLES.put(DyeColor.MAGENTA, Items.MAGENTA_CANDLE);
        CANDLES.put(DyeColor.ORANGE, Items.ORANGE_CANDLE);
        CANDLES.put(DyeColor.PINK, Items.PINK_CANDLE);
        CANDLES.put(DyeColor.PURPLE, Items.PURPLE_CANDLE);
        CANDLES.put(DyeColor.RED, Items.RED_CANDLE);
        CANDLES.put(DyeColor.WHITE, Items.WHITE_CANDLE);
        CANDLES.put(DyeColor.YELLOW, Items.YELLOW_CANDLE);

    }

    public static final CachedSupplier<Block> CARCASS = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "carcass")));

}