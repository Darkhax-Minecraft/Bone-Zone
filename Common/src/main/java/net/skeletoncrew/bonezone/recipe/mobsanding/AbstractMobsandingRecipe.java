package net.skeletoncrew.bonezone.recipe.mobsanding;

import net.darkhax.bookshelf.api.data.recipes.RecipeBaseData;
import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.darkhax.bookshelf.api.registry.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.skeletoncrew.bonezone.Constants;

public abstract class AbstractMobsandingRecipe extends RecipeBaseData<Container> {

    public static final CachedSupplier<RecipeType<AbstractMobsandingRecipe>> RECIPE_TYPE = RegistryObject.deferred(Registry.RECIPE_TYPE, Constants.MOD_ID, "mobsanding").cast();

    public AbstractMobsandingRecipe(ResourceLocation id) {

        super(id);
    }

    public abstract boolean canCraft(Level worldLevel, BlockPos pos, Entity target);

    public void onCrafted(Level worldLevel, BlockPos pos, Entity target) {

    }

    public void playSound(Level worldLevel, BlockPos pos, Entity target) {

        worldLevel.playSound(null, pos, SoundEvents.SKELETON_HURT, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public RecipeType<?> getType() {

        return RECIPE_TYPE.get();
    }

    public static AbstractMobsandingRecipe findRecipe(Level worldLevel, BlockPos pos, Entity target) {

        for (final AbstractMobsandingRecipe recipe : worldLevel.getRecipeManager().getAllRecipesFor(RECIPE_TYPE.get())) {

            if (recipe.canCraft(worldLevel, pos, target)) {

                return recipe;
            }
        }

        return null;
    }
}
