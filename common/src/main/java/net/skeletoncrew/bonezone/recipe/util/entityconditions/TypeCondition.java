package net.skeletoncrew.bonezone.recipe.util.entityconditions;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TypeCondition implements EntityCondition {

    private final Set<EntityType<?>> validTypes;

    public TypeCondition(EntityType<?>... validTypes) {

        this(new HashSet<>(Arrays.asList(validTypes)));
    }

    public TypeCondition(Set<EntityType<?>> validTypes) {

        this.validTypes = validTypes;
    }

    @Override
    public boolean test(Entity entity) {

        return this.validTypes.contains(entity.getType());
    }
}