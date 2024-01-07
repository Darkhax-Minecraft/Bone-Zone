package net.skeletoncrew.bonezone.recipe.mobsanding;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.api.data.bytebuf.BookshelfByteBufs;
import net.darkhax.bookshelf.api.data.codecs.BookshelfCodecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Set;

public class MobsandingRecipeSerializer implements RecipeSerializer<MobsandingRecipe> {

    private static final Codec<MobsandingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BookshelfCodecs.ENTITY_TYPE.getSet("condition", MobsandingRecipe::getMobs),
            MobsandingRecipe.DropEntry.DROP_CODEC.getArray("drops", MobsandingRecipe::getDrops)
    ).apply(instance, MobsandingRecipe::new));

    @Override
    public Codec<MobsandingRecipe> codec() {
        return CODEC;
    }

    @Override
    public MobsandingRecipe fromNetwork(FriendlyByteBuf buffer) {
        final Set<EntityType<?>> conditions = BookshelfByteBufs.ENTITY_TYPE.readSet(buffer);
        final MobsandingRecipe.DropEntry[] drops = MobsandingRecipe.DropEntry.DROP_BUFFER.readArray(buffer);
        return new MobsandingRecipe(conditions, drops);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, MobsandingRecipe toWrite) {
        BookshelfByteBufs.ENTITY_TYPE.writeSet(buffer, toWrite.getMobs());
        MobsandingRecipe.DropEntry.DROP_BUFFER.writeArray(buffer, toWrite.getDrops());
    }
}
