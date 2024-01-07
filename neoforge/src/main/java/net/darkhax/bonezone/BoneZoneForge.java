package net.darkhax.bonezone;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import net.skeletoncrew.bonezone.BoneZoneCommon;
import net.skeletoncrew.bonezone.Constants;
import net.skeletoncrew.bonezone.feature.CarcassFeature;

@Mod(Constants.MOD_ID)
public class BoneZoneForge {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Constants.MOD_ID);
    public static final RegistryObject<Feature<?>> BIRD = FEATURES.register("carcass_bird", CarcassFeature::new);
    public static final RegistryObject<Feature<?>> DEER = FEATURES.register("carcass_deer", CarcassFeature::new);
    public static final RegistryObject<Feature<?>> GOAT = FEATURES.register("carcass_goat", CarcassFeature::new);

    public BoneZoneForge() {

        new BoneZoneCommon();
        FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}