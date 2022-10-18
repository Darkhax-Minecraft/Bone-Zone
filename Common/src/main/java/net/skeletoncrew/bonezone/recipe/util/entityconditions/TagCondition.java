package net.skeletoncrew.bonezone.recipe.util.entityconditions;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class TagCondition implements EntityCondition {

    private final TagKey<EntityType<?>> tag;

    public TagCondition(TagKey<EntityType<?>> tag) {

        this.tag = tag;
    }

    @Override
    public boolean test(Entity entity) {

        return entity.getType().is(this.tag);
    }
}