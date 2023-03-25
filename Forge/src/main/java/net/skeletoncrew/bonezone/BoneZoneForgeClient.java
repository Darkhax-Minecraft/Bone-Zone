package net.skeletoncrew.bonezone;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenu;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverScreen;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BoneZoneForgeClient {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {

            MenuScreens.register((MenuType<BonecarverMenu>) Constants.BONECARVER_MENU.get(), BonecarverScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {

        // Block Colors
        final BlockColor plantColorProvider = (state, worldLevel, pos, i) -> worldLevel != null && pos != null ? BiomeColors.getAverageGrassColor(worldLevel, pos) : GrassColor.get(0.5, 1.0);
        event.register(plantColorProvider, Constants.SKELETON_FERN.get(), Constants.WITHER_FERN.get(), Constants.CREEPER_FERN.get(), Constants.FLIPPED_SKELETON_FERN.get(), Constants.FLIPPED_WITHER_FERN.get(), Constants.FLIPPED_CREEPER_FERN.get(), Constants.STRAY_FERN.get(), Constants.FLIPPED_STRAY_FERN.get());
    }
}
