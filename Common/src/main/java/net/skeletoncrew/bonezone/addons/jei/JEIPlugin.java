//package net.skeletoncrew.bonezone.addons.jei;
//
//import com.mojang.datafixers.kinds.Const;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.registration.IRecipeCatalystRegistration;
//import mezz.jei.api.registration.IRecipeCategoryRegistration;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.RecipeManager;
//import net.skeletoncrew.bonezone.Constants;
//import net.skeletoncrew.bonezone.addons.jei.bonecarving.BonecarvingCategory;
//import net.skeletoncrew.bonezone.recipe.bonecarving.AbstractBonecarvingRecipe;
//
//import java.util.List;
//
//@JeiPlugin
//public class JEIPlugin implements IModPlugin {
//
//    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(Constants.MOD_ID, "jei_support");
//    public static final RecipeType<AbstractBonecarvingRecipe> BONECARVING = RecipeType.create(Constants.MOD_ID, "bonecarving", AbstractBonecarvingRecipe .class);
//
//    @Override
//    public ResourceLocation getPluginUid() {
//
//        return PLUGIN_ID;
//    }
//
//    @Override
//    public void registerCategories(IRecipeCategoryRegistration registration) {
//
//        registration.addRecipeCategories(new BonecarvingCategory(registration.getJeiHelpers().getGuiHelper()));
//    }
//
//    @Override
//    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//
//        registration.addRecipeCatalyst(new ItemStack(Constants.BONECARVER.get()), BONECARVING);
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration registration) {
//
//        final RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
//        final List<AbstractBonecarvingRecipe> recipes = recipeManager.getAllRecipesFor(AbstractBonecarvingRecipe.RECIPE_TYPE.get());
//        registration.addRecipes(BONECARVING, recipes);
//    }
//}