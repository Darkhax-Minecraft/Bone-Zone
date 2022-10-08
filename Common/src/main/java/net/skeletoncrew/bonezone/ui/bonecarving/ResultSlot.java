package net.skeletoncrew.bonezone.ui.bonecarving;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ResultSlot extends Slot {

    private final BonecarverMenu owningMenu;

    protected ResultSlot(BonecarverMenu owningMenu, Container container, int slotId, int x, int y) {

        super(container, slotId, x, y);
        this.owningMenu = owningMenu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {

        // Users can't place anything. It's an output slot.
        return false;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {

        owningMenu.onItemCrafted(player, stack, owningMenu.getSelectedRecipe());
        super.onTake(player, stack);
    }
}