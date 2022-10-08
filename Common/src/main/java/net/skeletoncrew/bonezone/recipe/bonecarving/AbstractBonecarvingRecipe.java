package net.skeletoncrew.bonezone.recipe.bonecarving;

import net.darkhax.bookshelf.api.data.recipes.RecipeBaseData;
import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.darkhax.bookshelf.api.registry.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.skeletoncrew.bonezone.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBonecarvingRecipe extends RecipeBaseData<Container> {

    public static final CachedSupplier<RecipeType<AbstractBonecarvingRecipe>> RECIPE_TYPE = RegistryObject.deferred(Registry.RECIPE_TYPE, Constants.MOD_ID, "bonecarving").cast();

    public AbstractBonecarvingRecipe(ResourceLocation id) {

        super(id);
    }

    /**
     * Determines if the recipe can be crafted.
     *
     * @param player     The player trying to craft the recipe.
     * @param worldLevel The world the player is in.
     * @param pos        The position of the bonecarver.
     * @param inputStack The input item.
     * @return Can the recipe be crafted?
     */
    public abstract boolean canCraft(Player player, Level worldLevel, BlockPos pos, ItemStack inputStack);

    /**
     * Called after the recipe has been crafted.
     *
     * @param player     The player crafting the recipe.
     * @param worldLevel The world the player is in.
     * @param pos        The position of the bonecarver.
     * @param output     The item that was crafted.
     */
    public void onCrafted(Player player, Level worldLevel, BlockPos pos, ItemStack output) {

    }

    /**
     * When the recipe is crafted it will normally play a sound. This controls the sound played.
     *
     * @param player     The player that crafted the recipe.
     * @param worldLevel The world the player is in.
     * @param pos        The position of the bonecarver.
     * @param output     The item that was crafted.
     */
    public void playSound(Player player, Level worldLevel, BlockPos pos, ItemStack output) {

        worldLevel.playSound(null, pos, SoundEvents.SKELETON_HURT, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public void playSelectionSound(Player player, Level worldLevel, BlockPos pos) {


    }

    @Override
    public RecipeType<?> getType() {

        return RECIPE_TYPE.get();
    }

    /**
     * Checks if a recipe exists within the given context.
     *
     * @param player     The player crafting the recipe.
     * @param worldLevel The world the player is in.
     * @param pos        The position of the bonecarver block.
     * @param input      The item being used.
     * @return Is there a valid recipe?
     */
    public static boolean hasRecipe(Player player, Level worldLevel, BlockPos pos, ItemStack input) {

        for (final AbstractBonecarvingRecipe recipe : worldLevel.getRecipeManager().getAllRecipesFor(RECIPE_TYPE.get())) {

            if (recipe.canCraft(player, worldLevel, pos, input)) {

                return true;
            }
        }

        return false;
    }

    /**
     * Collects a list of all valid recipes for the given context.
     *
     * @param player     The player crafting the recipe.
     * @param worldLevel The world the player is in.
     * @param pos        The position of the bonecarver block.
     * @param input      The input item.
     * @return A list of all valid recipes.
     */
    public static List<AbstractBonecarvingRecipe> findRecipes(Player player, Level worldLevel, BlockPos pos, ItemStack input) {

        final List<AbstractBonecarvingRecipe> validRecipes = new ArrayList<>();

        for (final AbstractBonecarvingRecipe recipe : worldLevel.getRecipeManager().getAllRecipesFor(RECIPE_TYPE.get())) {

            if (recipe.canCraft(player, worldLevel, pos, input)) {

                validRecipes.add(recipe);
            }
        }

        return validRecipes;
    }
}