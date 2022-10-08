package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenu;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverScreen;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BoneZoneForgeClient {

    private static final CachedSupplier<MenuType<?>> MENU_TYPE = CachedSupplier.cache(() -> Registry.MENU.get(new ResourceLocation(Constants.MOD_ID, "bonecarver")));

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {

            final MenuType<BonecarverMenu> menu = (MenuType<BonecarverMenu>) MENU_TYPE.get();
            MenuScreens.register(menu, BonecarverScreen::new);
        });
    }
}
