package net.skeletoncrew.bonezone.ui.bonecarving;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.skeletoncrew.bonezone.Constants;
import net.skeletoncrew.bonezone.recipe.bonecarving.AbstractBonecarvingRecipe;

import java.util.List;

public class BonecarverScreen extends AbstractContainerScreen<BonecarverMenu> {

    private static final ResourceLocation BG_LOCATION = new ResourceLocation(Constants.MOD_ID, "textures/gui/container/bonecarver.png");
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public BonecarverScreen(BonecarverMenu menu, Inventory playerInv, Component title) {

        super(menu, playerInv, title);
        menu.container.setChangeListener(this::containerChanged);
        this.titleLabelY--;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int x, int y) {

        // Setup
        this.renderBackground(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG_LOCATION);

        // GUI Background
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Scroll bar
        final int scrollDepth = (int) (41.0F * this.scrollOffs);
        this.blit(poseStack, this.leftPos + 119, this.topPos + 15 + scrollDepth, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);

        // recipe buttons
        final int recipesX = this.leftPos + 52;
        final int recipesY = this.topPos + 14;
        final int lastVisible = this.startIndex + 12;
        this.renderButtons(poseStack, x, y, recipesX, recipesY, lastVisible);
        this.renderRecipes(poseStack, recipesX, recipesY, lastVisible);
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {

        super.renderTooltip(poseStack, mouseX, mouseY);

        if (this.displayRecipes) {

            final int recipesX = this.leftPos + 52;
            final int recipesY = this.topPos + 14;
            final int lastRecipeIndex = this.startIndex + 12;
            final List<AbstractBonecarvingRecipe> availableRecipes = this.menu.getAvailableRecipes();

            for (int recipeIndex = this.startIndex; recipeIndex < lastRecipeIndex && recipeIndex < this.menu.getRecipeCount(); recipeIndex++) {

                final int buttonId = recipeIndex - this.startIndex;
                final int buttonX = recipesX + buttonId % 4 * 16;
                final int buttonY = recipesY + buttonId / 4 * 18 + 2;

                if (mouseX >= buttonX && mouseX < buttonX + 16 && mouseY >= buttonY && mouseY < buttonY + 18) {

                    this.renderTooltip(poseStack, availableRecipes.get(recipeIndex).getResultItem(), mouseX, mouseY);
                }
            }
        }
    }

    private void renderButtons(PoseStack poseStack, int mouseX, int mouseY, int recipesX, int recipesY, int lastRecipe) {

        for (int recipeIndex = this.startIndex; recipeIndex < lastRecipe && recipeIndex < this.menu.getRecipeCount(); recipeIndex++) {

            final int buttonId = recipeIndex - this.startIndex;
            final int buttonX = recipesX + buttonId % 4 * 16;
            final int buttonY = recipesY + (buttonId / 4) * 18 + 2;

            // Default texture 0, 166 to 15, 183
            int textureUVOffset = this.imageHeight;

            // Switch to the selected recipe button texture (0,184 to 15,201)
            if (recipeIndex == this.menu.getSelectedRecipeIndex()) {

                textureUVOffset += 18;
            }

            // Switch to the hovered recipe button texture (0,202 to 15, 219)
            else if (mouseX >= buttonX && mouseY >= buttonY && mouseX < buttonX + 16 && mouseY < buttonY + 18) {
                textureUVOffset += 36;
            }

            // Renders the button texture. 16x18 rectangle
            this.blit(poseStack, buttonX, buttonY - 1, 0, textureUVOffset, 16, 18);
        }

    }

    private void renderRecipes(PoseStack pose, int recipesX, int recipesY, int lastVisibleRecipe) {

        final List<AbstractBonecarvingRecipe> recipes = this.menu.getAvailableRecipes();

        for (int recipeIndex = this.startIndex; recipeIndex < lastVisibleRecipe && recipeIndex < this.menu.getRecipeCount(); recipeIndex++) {

            final int buttonId = recipeIndex - this.startIndex;
            final int buttonX = recipesX + buttonId % 4 * 16;
            final int buttonY = recipesY + (buttonId / 4) * 18 + 2;

            this.minecraft.getItemRenderer().renderAndDecorateItem(pose, recipes.get(recipeIndex).getResultItem(), buttonX, buttonY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        this.scrolling = false;

        if (this.displayRecipes) {

            final int recipeBoxX = this.leftPos + 52;
            final int recipeBoxY = this.topPos + 14;
            final int lastRecipeIndex = this.startIndex + 12;

            // TODO only run code if inside bounds
            for (int recipeIndex = this.startIndex; recipeIndex < lastRecipeIndex; recipeIndex++) {

                final int buttonId = recipeIndex - this.startIndex;
                final double buttonX = mouseX - (double) (recipeBoxX + buttonId % 4 * 16);
                final double buttonY = mouseY - (double) (recipeBoxY + buttonId / 4 * 18);

                if (buttonX >= 0.0 && buttonY >= 0.0 && buttonX < 16.0 && buttonY < 18.0 && this.menu.clickMenuButton(this.minecraft.player, recipeIndex)) {

                    // TODO don't play sound if the button is already selected
                    // TODO get sound from recipe
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BONE_BLOCK_PLACE, 1.0F));

                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, recipeIndex);
                    return true;
                }
            }

            final int scrollbarX = this.leftPos + 119;
            final int scrollbarY = this.topPos + 9;

            if (mouseX >= (double) scrollbarX && mouseX < (double) (scrollbarX + 12) && mouseY >= (double) scrollbarY && mouseY < (double) (scrollbarY + 54)) {

                this.scrolling = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {

        if (this.scrolling && this.isScrollBarActive()) {

            final int scrollbarStart = this.topPos + 14;
            final int scrollbarStop = scrollbarStart + 54;

            this.scrollOffs = ((float) mouseY - (float) scrollbarStart - 7.5F) / ((float) (scrollbarStop - scrollbarStart) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) this.getOffscreenRows()) + 0.5) * 4;
            return true;
        }

        else {

            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollAmount) {

        if (this.isScrollBarActive()) {

            final int offscreenRows = this.getOffscreenRows();
            final float offsetDelta = (float) scrollAmount / (float) offscreenRows;

            this.scrollOffs = Mth.clamp(this.scrollOffs - offsetDelta, 0.0F, 1.0F);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) offscreenRows) + 0.5) * 4;
        }

        return true;
    }

    private boolean isScrollBarActive() {

        return this.displayRecipes && this.menu.getRecipeCount() > 12;
    }

    protected int getOffscreenRows() {

        return (this.menu.getRecipeCount() + 4 - 1) / 4 - 3;
    }

    private void containerChanged() {

        this.displayRecipes = this.menu.hasInputItem();

        if (!this.displayRecipes) {

            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
}