package net.skeletoncrew.bonezone.recipe.util.entityconditions;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class EntityPredicateCondition implements EntityCondition {

    private final EntityPredicate predicate;

    public EntityPredicateCondition(EntityPredicate predicate) {

        this.predicate = predicate;
    }

    @Override
    public boolean test(Entity entity) {

        if (entity.level() instanceof ServerLevel serverLevel) {

            return predicate.matches(serverLevel, entity.position(), entity);
        }

        return false;
    }
}
