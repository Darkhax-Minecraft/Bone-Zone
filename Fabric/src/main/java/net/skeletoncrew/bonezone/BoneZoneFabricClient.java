package net.skeletoncrew.bonezone;

import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverMenu;
import net.skeletoncrew.bonezone.ui.bonecarving.BonecarverScreen;

public class BoneZoneFabricClient implements ClientModInitializer {

    private static final CachedSupplier<MenuType<?>> MENU_TYPE = CachedSupplier.cache(() -> Registry.MENU.get(new ResourceLocation(Constants.MOD_ID, "bonecarver")));

    @Override
    public void onInitializeClient() {

        MenuType<BonecarverMenu> menu = (MenuType<BonecarverMenu>) MENU_TYPE.get();
        MenuScreens.register(menu, BonecarverScreen::new);
    }
}