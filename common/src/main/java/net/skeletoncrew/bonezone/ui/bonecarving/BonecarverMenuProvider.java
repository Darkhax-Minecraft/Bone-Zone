package net.skeletoncrew.bonezone.ui.bonecarving;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.bookshelf.api.data.bytebuf.BookshelfByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import javax.annotation.Nullable;

public class BonecarverMenuProvider implements MenuProvider {

    private final BlockPos carverPos;
    private final Component windowName;

    private BonecarverMenuProvider(BlockPos carverPos, Component windowName) {

        this.carverPos = carverPos;
        this.windowName = windowName;
    }

    @Override
    public Component getDisplayName() {

        return this.windowName;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player player) {

        return new BonecarverMenu(windowId, playerInv, this.carverPos);
    }

    public static void openMenu(ServerPlayer serverPlayer, BlockPos carverPos, Component title) {

        final BonecarverMenuProvider provider = new BonecarverMenuProvider(carverPos, title);
        Services.INVENTORY_HELPER.openMenu(serverPlayer, provider, buf -> BookshelfByteBufs.BLOCK_POS.write(buf, carverPos));
    }
}
