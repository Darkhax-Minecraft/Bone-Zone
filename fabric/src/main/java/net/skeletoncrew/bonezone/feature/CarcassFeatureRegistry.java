package net.skeletoncrew.bonezone.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.skeletoncrew.bonezone.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum CarcassFeatureRegistry {

    INSTANCE;

    private static final ResourceLocation BIRD_FEATURE = new ResourceLocation(Constants.MOD_ID, "carcass_bird");
    private static final ResourceLocation DEER_FEATURE = new ResourceLocation(Constants.MOD_ID, "carcass_deer");
    private static final ResourceLocation GOAT_FEATURE = new ResourceLocation(Constants.MOD_ID, "carcass_goat");
    private static final ResourceLocation BULL_FEATURE = new ResourceLocation(Constants.MOD_ID, "carcass_bull");

    private static final List<String> BIRD_BIOMES = new ArrayList<>();
    private static final List<String> DEER_BIOMES = new ArrayList<>();
    private static final List<String> GOAT_BIOMES = new ArrayList<>();
    private static final List<String> BULL_BIOMES = new ArrayList<>();

    static {
        BIRD_BIOMES.add("taiga");
        BIRD_BIOMES.add("plains");
        BIRD_BIOMES.add("sunflower_plains");
        BIRD_BIOMES.add("forest");
        BIRD_BIOMES.add("dark_forest");
        BIRD_BIOMES.add("meadow");
    }

    static {
        DEER_BIOMES.add("desert");
        DEER_BIOMES.add("savanna");
        DEER_BIOMES.add("savanna_plateau");
        DEER_BIOMES.add("badlands");
        DEER_BIOMES.add("eroded_badlands");
        DEER_BIOMES.add("river");
        DEER_BIOMES.add("dark_forest");
    }

    static {
        GOAT_BIOMES.add("windswept_hills");
        GOAT_BIOMES.add("stony_shore");
        GOAT_BIOMES.add("stony_peaks");
        GOAT_BIOMES.add("jagged_peaks");
        GOAT_BIOMES.add("dark_forest");
        GOAT_BIOMES.add("windswept_gravely_hills");
    }

    static {
        BULL_BIOMES.add("stony_shore");
        BULL_BIOMES.add("dark_forest");
        BULL_BIOMES.add("badlands");
        BULL_BIOMES.add("eroded_badlands");
        BULL_BIOMES.add("river");
        BULL_BIOMES.add("savanna_plateau");
    }

    public void register() {
        registerFeature(BIRD_FEATURE, CarcassFeature::new, BIRD_BIOMES);
        registerFeature(DEER_FEATURE, CarcassFeature::new, DEER_BIOMES);
        registerFeature(GOAT_FEATURE, CarcassFeature::new, GOAT_BIOMES);
        registerFeature(BULL_FEATURE, CarcassFeature::new, BULL_BIOMES);
    }

    private void registerFeature(ResourceLocation feature, Supplier<CarcassFeature> carcass, List<String> biomes) {
        Registry.register(BuiltInRegistries.FEATURE, feature, carcass.get());
        BiomeModifications.addFeature(
                ctx -> ctx.getBiomeKey().location().getNamespace().equals("minecraft") && biomes.contains(ctx.getBiomeKey().location().getPath()),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                ResourceKey.create(Registries.PLACED_FEATURE, feature));
    }
}
