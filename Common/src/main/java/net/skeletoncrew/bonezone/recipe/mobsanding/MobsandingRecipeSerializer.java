package net.skeletoncrew.bonezone.recipe.mobsanding;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import net.darkhax.bookshelf.api.serialization.Serializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.skeletoncrew.bonezone.recipe.util.entityconditions.EntityCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobsandingRecipeSerializer implements RecipeSerializer<MobsandingRecipe> {

    @Override
    public MobsandingRecipe fromJson(ResourceLocation id, JsonObject json) {

        final EntityCondition conditions = EntityCondition.fromJSON(json.get("condition"));
        final MobsandingRecipe.DropEntry[] drops = fromJson(json.get("drops"));
        return new MobsandingRecipe(id, conditions, drops);
    }

    @Override
    public MobsandingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {

        // TODO this is not needed on the client for now but likely will be at a later point.
        return new MobsandingRecipe(id, entity -> false, new MobsandingRecipe.DropEntry[0]);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, MobsandingRecipe recipe) {

        // TODO we do not write data to the client, but we should for stuff like JEI.
    }

    public static MobsandingRecipe.DropEntry[] fromJson(JsonElement json) {

        if (json instanceof JsonPrimitive primitive && primitive.isString()) {

            final Item dropItem = Serializers.ITEM.fromJSON(json);

            if (dropItem != null) {

                return new MobsandingRecipe.DropEntry[] {new MobsandingRecipe.DropEntry(dropItem.getDefaultInstance(), 1f)};
            }
        }

        if (json instanceof JsonArray array) {

            final List<MobsandingRecipe.DropEntry> drops = new ArrayList<>();

            for (JsonElement subElement : array) {

                drops.addAll(Arrays.asList(fromJson(subElement)));
            }

            return drops.toArray(MobsandingRecipe.DropEntry[]::new);
        }

        if (json instanceof JsonObject obj) {

            final ItemStack stack = Serializers.ITEM_STACK.fromJSON(obj, "result");
            final float chance = Serializers.FLOAT.fromJSON(obj, "chance", 1f);
            return new MobsandingRecipe.DropEntry[] {new MobsandingRecipe.DropEntry(stack, chance)};
        }

        throw new JsonParseException("Invalid drop entry! Expected string, array, or object.");
    }
}
