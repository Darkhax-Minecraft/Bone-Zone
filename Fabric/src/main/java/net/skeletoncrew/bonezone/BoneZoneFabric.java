package net.skeletoncrew.bonezone;

import net.fabricmc.api.ModInitializer;

public class BoneZoneFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        new BoneZoneCommon();
    }
}