package net.skeletoncrew.bonezone.addons.jei.bonecarving;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.skeletoncrew.bonezone.addons.jei.JEIPlugin;
import net.skeletoncrew.bonezone.recipe.bonecarving.AbstractBonecarvingRecipe;
import net.skeletoncrew.bonezone.recipe.bonecarving.BonecarvingRecipe;

public class BonecarvingCategory implements IRecipeCategory<AbstractBonecarvingRecipe> {

    public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation("jei", "textures/jei/gui/gui_vanilla.png");
    public static final int width = 82;
    public static final int height = 34;

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public BonecarvingCategory(IGuiHelper guiHelper) {

        background = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 0, 220, width, height);
        icon = guiHelper.createDrawableItemStack(new ItemStack(Blocks.STONECUTTER));
        localizedName = Component.translatable("gui.jei.category.bonezone.bonecarving");
    }

    @Override
    public RecipeType<AbstractBonecarvingRecipe> getRecipeType() {

        return JEIPlugin.BONECARVING;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AbstractBonecarvingRecipe abs, IFocusGroup focuses) {

        if (abs instanceof BonecarvingRecipe recipe) {

            builder.addSlot(RecipeIngredientRole.INPUT, 1, 9).addIngredients(recipe.input);
            builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 9).addItemStack(recipe.getResultItem(null));
        }
    }
}
