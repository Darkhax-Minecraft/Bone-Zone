package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.skeletoncrew.bonezone.block.CustomPotBlock;
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

    // Menus
    public static final CachedSupplier<MenuType<?>> BONECARVER_MENU = CachedSupplier.cache(() -> Registry.MENU.get(new ResourceLocation(Constants.MOD_ID, "bonecarver")));

    // Biomecolored plant pots
    public static final CachedSupplier<Block> SKELETON_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "skeleton_potted_fern")));
    public static final CachedSupplier<Block> WITHER_FERN = CachedSupplier.cache(() -> Registry.BLOCK.get(new ResourceLocation(Constants.MOD_ID, "wither_potted_fern")));

}