package net.skeletoncrew.bonezone;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.GrassColor;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenu;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverScreen;

public class BoneZoneFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Screens
        MenuScreens.register((MenuType<BonecarverMenu>) Constants.BONECARVER_MENU.get(), BonecarverScreen::new);

        // Block Colors
        final BlockColor plantColorProvider = (state, worldLevel, pos, i) -> worldLevel != null && pos != null ? BiomeColors.getAverageGrassColor(worldLevel, pos) : GrassColor.get(0.5, 1.0);
        ColorProviderRegistry.BLOCK.register(plantColorProvider, Constants.SKELETON_FERN.get(), Constants.WITHER_FERN.get(), Constants.CREEPER_FERN.get(), Constants.FLIPPED_SKELETON_FERN.get(), Constants.FLIPPED_WITHER_FERN.get(), Constants.FLIPPED_CREEPER_FERN.get());
    }
}