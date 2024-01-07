package net.skeletoncrew.bonezone.recipe.bonecarving;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.api.data.bytebuf.BookshelfByteBufs;
import net.darkhax.bookshelf.api.data.codecs.BookshelfCodecs;
import net.darkhax.bookshelf.api.data.sound.Sound;
import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BonecarvingRecipeSerializer implements RecipeSerializer<BonecarvingRecipe> {

    private static CachedSupplier<Sound> DEFAULT_CRAFTING_SOUND = CachedSupplier.cache(() -> new Sound(SoundEvents.SKELETON_HURT, SoundSource.BLOCKS, 1.0F, 1.0F));
    private static Codec<BonecarvingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BookshelfCodecs.INGREDIENT.get("input", BonecarvingRecipe::getInput),
            BookshelfCodecs.ITEM_STACK_FLEXIBLE.get("output", BonecarvingRecipe::getOutput),
            BookshelfCodecs.SOUND.get("crafting_sound", BonecarvingRecipe::getCraftingSound, DEFAULT_CRAFTING_SOUND.get())
    ).apply(instance, BonecarvingRecipe::new));

    @Override
    public Codec<BonecarvingRecipe> codec() {

        return CODEC;
    }

    @Override
    public BonecarvingRecipe fromNetwork(FriendlyByteBuf buffer) {

        final Ingredient input = BookshelfByteBufs.INGREDIENT.read(buffer);
        final ItemStack output = BookshelfByteBufs.ITEM_STACK.read(buffer);
        final Sound sound = BookshelfByteBufs.SOUND.read(buffer);

        return new BonecarvingRecipe(input, output, sound);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, BonecarvingRecipe recipe) {

        BookshelfByteBufs.INGREDIENT.write(buf, recipe.getInput());
        BookshelfByteBufs.ITEM_STACK.write(buf, recipe.getOutput());
        BookshelfByteBufs.SOUND.write(buf, recipe.getCraftingSound());
    }
}
