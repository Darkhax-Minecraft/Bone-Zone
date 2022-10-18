package net.skeletoncrew.bonezone.recipe.util.entityconditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.Objects;
import java.util.function.Predicate;

public interface EntityCondition extends Predicate<Entity> {

    default EntityCondition or(EntityCondition other) {

        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    static EntityCondition fromString(String value) {

        if (value.startsWith("#")) {

            return new TagCondition(TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(value.substring(1))));
        }

        else {

            final ResourceLocation id = new ResourceLocation(value);
            final EntityType<?> type = Registry.ENTITY_TYPE.containsKey(id) ? Registry.ENTITY_TYPE.get(id) : null;

            if (type != null) {

                return new TypeCondition(type);
            }

            throw new JsonParseException("Invalid entity ID " + value);
        }
    }

    static EntityCondition fromJSON(JsonElement json) {

        // Allows assigning value as just a string.
        // #minecraft:skeletons - Tag of all skeletons
        // minecraft:pig - Just the pig
        if (json instanceof JsonPrimitive primitive && primitive.isString()) {

            return fromString(primitive.getAsString());
        }

        // Allow an array of different condition types to become a multi-or condition.
        // This example will match just the pig, anything in the skeletons tag, or anything
        // stepping inside the vanilla beach biome.
        // ["minecraft:pig", "#minecraft:skeletons", {"stepping_on":{"biome":"minecraft:beach"}}]
        else if (json instanceof JsonArray array) {

            EntityCondition chainedConditions = null;

            for (JsonElement element : array) {

                final EntityCondition parsedCondition = fromJSON(element);
                chainedConditions = (chainedConditions == null) ? parsedCondition : chainedConditions.or(parsedCondition);
            }

            if (chainedConditions == null) {

                throw new JsonParseException("Failed to parse condition array. No valid entries contained!");
            }

            return chainedConditions;
        }

        // Default to Mojang's entity predicate spec which is very complex.
        // {
        //  "equipment": {
        //    "mainhand": {
        //      "tag": "minecraft:boats",
        //      "count": {
        //        "min": 1,
        //        "max": 5
        //      }
        //    }
        //  }
        //}
        else if (json instanceof JsonObject obj) {

            return new EntityPredicateCondition(EntityPredicate.fromJson(obj));
        }

        throw new JsonParseException("Can't parse a valid entity condition!");
    }
}