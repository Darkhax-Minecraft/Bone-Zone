package net.skeletoncrew.bonezone.recipe.bonecarving;

import com.google.gson.JsonObject;
import net.darkhax.bookshelf.api.data.sound.Sound;
import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.darkhax.bookshelf.api.serialization.Serializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BonecarvingRecipeSerializer implements RecipeSerializer<BonecarvingRecipe> {

    private static CachedSupplier<Sound> DEFAULT_CRAFTING_SOUND = CachedSupplier.cache(() -> new Sound(SoundEvents.SKELETON_HURT, SoundSource.BLOCKS, 1.0F, 1.0F));

    @Override
    public BonecarvingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

        final Ingredient input = Serializers.INGREDIENT.fromJSON(json, "input");
        final ItemStack output = Serializers.ITEM_STACK.fromJSON(json, "output");
        final Sound sound = Serializers.SOUND.fromJSON(json, "crafting_sound", DEFAULT_CRAFTING_SOUND.get());

        return new BonecarvingRecipe(recipeId, input, output, sound);
    }

    @Override
    public BonecarvingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {

        final Ingredient input = Serializers.INGREDIENT.fromByteBuf(buf);
        final ItemStack output = Serializers.ITEM_STACK.fromByteBuf(buf);
        final Sound sound = Serializers.SOUND.fromByteBuf(buf);

        return new BonecarvingRecipe(recipeId, input, output, sound);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, BonecarvingRecipe recipe) {

        Serializers.INGREDIENT.toByteBuf(buf, recipe.input);
        Serializers.ITEM_STACK.toByteBuf(buf, recipe.output);
        Serializers.SOUND.toByteBuf(buf, recipe.sound);
    }
}
