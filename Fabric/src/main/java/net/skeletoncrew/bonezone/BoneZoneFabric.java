package net.skeletoncrew.bonezone;

import net.fabricmc.api.ModInitializer;
import net.skeletoncrew.bonezone.feature.CarcassFeatureRegistry;

public class BoneZoneFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        new BoneZoneCommon();
        CarcassFeatureRegistry.INSTANCE.register();
    }
}