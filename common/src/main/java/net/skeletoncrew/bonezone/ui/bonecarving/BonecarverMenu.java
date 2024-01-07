package net.skeletoncrew.bonezone.ui.bonecarving;

import com.google.common.collect.Lists;
import net.darkhax.bookshelf.api.data.bytebuf.BookshelfByteBufs;
import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.skeletoncrew.bonezone.Constants;
import net.skeletoncrew.bonezone.block.BoneCarverBlock;
import net.skeletoncrew.bonezone.recipe.bonecarving.AbstractBonecarvingRecipe;

import javax.annotation.Nullable;
import java.util.List;

public class BonecarverMenu extends AbstractContainerMenu {

    private static final CachedSupplier<MenuType<?>> MENU_TYPE = CachedSupplier.cache(() -> BuiltInRegistries.MENU.get(new ResourceLocation(Constants.MOD_ID, "bonecarver")));

    private final BlockPos bonecarverPos;
    private final Inventory playerInv;
    protected final BonecarverContainer container;
    private final ResultContainer resultContainer;
    private final DataSlot selectedRecipeIndex;
    private final Slot inputSlot;
    private final Slot outputSlot;

    long lastSoundTime;
    private ItemStack input;
    private List<RecipeHolder<AbstractBonecarvingRecipe>> validRecipes;

    public BonecarverMenu(int windowId, Inventory playerInv, final BlockPos bonecarverPos) {

        super(MENU_TYPE.get(), windowId);

        // Setup menu with default state.
        this.bonecarverPos = bonecarverPos;
        this.playerInv = playerInv;
        this.container = new BonecarverContainer(this);
        this.resultContainer = new ResultContainer();
        this.selectedRecipeIndex = DataSlot.standalone();
        this.validRecipes = Lists.newArrayList();
        this.input = ItemStack.EMPTY;

        // Add the block's input and output slot.
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.outputSlot = this.addSlot(new ResultSlot(this, this.resultContainer, 1, 143, 33));

        // Add the player inventory
        for (int invY = 0; invY < 3; invY++) {
            for (int invX = 0; invX < 9; invX++) {
                this.addSlot(new Slot(playerInv, invX + invY * 9 + 9, 8 + invX * 18, 84 + invY * 18));
            }
        }

        // Add the player hotbar slots
        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            this.addSlot(new Slot(playerInv, hotbarSlot, 8 + hotbarSlot * 18, 142));
        }

        // Add a data slot that tracks the player's current recipe selection.
        this.addDataSlot(this.selectedRecipeIndex);
    }

    @Override
    public boolean stillValid(Player player) {

        final Level level = player.level();
        final BlockPos pos = this.bonecarverPos;

        // Prevent the player from accessing the UI if the block is moved or they are pushed too far away.
        return level.getBlockState(pos).getBlock() instanceof BoneCarverBlock && player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public boolean clickMenuButton(Player player, int button) {

        if (this.isValidRecipeIndex(button)) {

            this.selectedRecipeIndex.set(button);
            this.updateResults();
        }

        return true;
    }

    @Override
    public void slotsChanged(Container container) {

        final ItemStack inputStack = this.inputSlot.getItem();

        if (!inputStack.is(this.input.getItem())) {

            this.input = inputStack.copy();
            this.updateRecipes(inputStack);
        }
    }

    @Override
    public MenuType<?> getType() {

        return MENU_TYPE.get();
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {

        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {

        final Slot clickedSlot = this.slots.get(slotId);
        ItemStack unmovedItems = ItemStack.EMPTY;

        if (clickedSlot != null && clickedSlot.hasItem()) {

            ItemStack slotItem = clickedSlot.getItem();
            unmovedItems = slotItem.copy();

            // Move result slot contents into player inventory.
            if (slotId == 1) {

                slotItem.getItem().onCraftedBy(slotItem, player.level(), player);

                if (!this.moveItemStackTo(slotItem, 2, 38, true)) {

                    return ItemStack.EMPTY;
                }

                clickedSlot.onQuickCraft(slotItem, unmovedItems);
            }

            // Try to move crafting input back into inventory
            else if (slotId == 0) {

                if (!this.moveItemStackTo(slotItem, 2, 38, false)) {

                    return ItemStack.EMPTY;
                }
            }

            // Try to move potential recipe input into input slot.
            else if (AbstractBonecarvingRecipe.hasRecipe(player, player.level(), null, slotItem)) {

                if (!this.moveItemStackTo(slotItem, 0, 1, false)) {

                    return ItemStack.EMPTY;
                }
            }

            // Move from player inventory to hotbar. Vanilla behaviour.
            else if (slotId >= 2 && slotId < 29) {

                if (!this.moveItemStackTo(slotItem, 29, 38, false)) {

                    return ItemStack.EMPTY;
                }
            }

            // Move hotbar item to the player inventory. Vanilla behaviour.
            else if (slotId >= 29 && slotId < 38 && !this.moveItemStackTo(slotItem, 2, 29, false)) {

                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                clickedSlot.set(ItemStack.EMPTY);
            }

            clickedSlot.setChanged();
            if (slotItem.getCount() == unmovedItems.getCount()) {
                return ItemStack.EMPTY;
            }

            clickedSlot.onTake(player, slotItem);
            this.broadcastChanges();
        }

        return unmovedItems;
    }

    @Override
    public void removed(Player player) {

        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.clearContainer(player, this.container);
    }

    /**
     * Creates a new BonecarverMenu using network context. This takes advantage of the additional network context
     * provided by Forge/Fabric.
     *
     * @param windowId The windowId, used as a handle to track and sync menu data.
     * @param inv      The player inventory.
     * @param buf      A network buffer with additional properties that are not normally available.
     * @return The newly created menu.
     */
    public static BonecarverMenu fromNetwork(int windowId, Inventory inv, FriendlyByteBuf buf) {

        final BlockPos bonecarverPos = BookshelfByteBufs.BLOCK_POS.read(buf);
        return new BonecarverMenu(windowId, inv, bonecarverPos);
    }

    /**
     * Gets the amount of available recipes.
     *
     * @return The amount of available recipes.
     */
    protected int getRecipeCount() {

        return this.validRecipes.size();
    }

    /**
     * When the player crafts an item using the bonecarver we run through this logic. It is primarily used for updating
     * stats and the state of the menu. It is also used to provide recipes with additional hooks.
     *
     * @param player         The player that crafted the recipe.
     * @param craftingOutput The ItemStack that was crafted.
     * @param recipe         The recipe that was used to craft the item.
     */
    protected void onItemCrafted(Player player, ItemStack craftingOutput, AbstractBonecarvingRecipe recipe) {

        // Update achievements, events, and stats
        craftingOutput.onCraftedBy(player.level(), player, craftingOutput.getCount());
        this.resultContainer.awardUsedRecipes(player, List.of(craftingOutput));
        recipe.onCrafted(player, player.level(), this.bonecarverPos, craftingOutput);

        // If there are no input items left refresh results.
        if (!this.inputSlot.remove(1).isEmpty()) {

            this.updateResults();
        }

        // Attempt to play a crafting sound.
        final long worldTime = player.level().getGameTime();

        if (this.lastSoundTime != worldTime) {

            recipe.playSound(player, player.level(), this.bonecarverPos, craftingOutput);
            this.lastSoundTime = worldTime;
        }
    }

    /**
     * Updates the list of available recipes based on a new input.
     *
     * @param inputStack The new input item.
     */
    private void updateRecipes(ItemStack inputStack) {

        // Erase the current state of the inventory.
        this.validRecipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.outputSlot.set(ItemStack.EMPTY);

        // Populate with recipes for the current input.
        if (!inputStack.isEmpty()) {

            this.validRecipes = AbstractBonecarvingRecipe.findRecipes(this.playerInv.player, this.playerInv.player.level(), this.bonecarverPos, inputStack);
        }
    }

    /**
     * Updates the output slot to reflect the current state of the inventory.
     */
    private void updateResults() {

        // If a valid recipe is selected, set the output.
        final RecipeHolder<AbstractBonecarvingRecipe> selectedRecipe = this.getSelectedRecipe();

        if (selectedRecipe != null && selectedRecipe.value() != null) {

            this.resultContainer.setRecipeUsed(selectedRecipe);
            this.outputSlot.set(selectedRecipe.value().assemble(this.container, null));
        }

        // Invalid recipe, reset the output.
        else {

            this.outputSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    /**
     * Returns the index of the currently selected recipe.
     *
     * @return The index of the currently selected recipe.
     */
    public int getSelectedRecipeIndex() {

        return this.selectedRecipeIndex.get();
    }

    /**
     * Gets a list of the recipes that can currently be crafted based on the input and crafting context.
     *
     * @return A list of recipes available to craft.
     */
    public List<RecipeHolder<AbstractBonecarvingRecipe>> getAvailableRecipes() {

        return this.validRecipes;
    }

    /**
     * Gets the recipe that is currently selected by the player.
     *
     * @return The recipe that is currently selected by the player in the GUI. If no recipes are available, or the
     * recipe is invalid the result will be null.
     */
    @Nullable
    public RecipeHolder<AbstractBonecarvingRecipe> getSelectedRecipe() {

        return !this.validRecipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get()) ? this.validRecipes.get(this.selectedRecipeIndex.get()) : null;
    }

    /**
     * Checks if an item is in the input slot.
     *
     * @return If there is an item in the input slot.
     */
    public boolean hasInputItem() {

        return this.inputSlot.hasItem() && !this.validRecipes.isEmpty();
    }

    /**
     * Verifies that a recipe index maps to an actual recipe.
     *
     * @param index The recipe index to verify.
     * @return If the recipe index is valid.
     */
    private boolean isValidRecipeIndex(int index) {

        return index >= 0 && index < this.validRecipes.size();
    }
}