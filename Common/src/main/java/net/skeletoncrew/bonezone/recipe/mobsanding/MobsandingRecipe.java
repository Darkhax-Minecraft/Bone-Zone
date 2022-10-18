package net.skeletoncrew.bonezone.recipe.mobsanding;

import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.darkhax.bookshelf.api.registry.RegistryObject;
import net.darkhax.bookshelf.api.util.MathsHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.skeletoncrew.bonezone.Constants;
import net.skeletoncrew.bonezone.recipe.util.entityconditions.EntityCondition;

public class MobsandingRecipe extends AbstractMobsandingRecipe {

    private static final CachedSupplier<RecipeSerializer<?>> SERIALIZER = RegistryObject.deferred(Registry.RECIPE_SERIALIZER, Constants.MOD_ID, "mobsanding").cast();

    private final EntityCondition condition;
    private final DropEntry[] drops;

    public MobsandingRecipe(ResourceLocation id, EntityCondition condition, DropEntry[] drops) {

        super(id);
        this.condition = condition;
        this.drops = drops;
    }

    @Override
    public boolean canCraft(Level worldLevel, BlockPos pos, Entity target) {

        return condition.test(target);
    }

    @Override
    public void onCrafted(Level worldLevel, BlockPos pos, Entity target) {

        if (!worldLevel.isClientSide) {

            for (DropEntry drop : this.drops) {

                if (MathsHelper.tryPercentage(drop.chance)) {

                    target.spawnAtLocation(drop.item.copy());
                }
            }
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return SERIALIZER.get();
    }

    public static class DropEntry {

        public ItemStack item;
        public float chance;

        public DropEntry(ItemStack item, float chance) {

            this.item = item;
            this.chance = chance;
        }
    }
}