package net.skeletoncrew.bonezone;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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