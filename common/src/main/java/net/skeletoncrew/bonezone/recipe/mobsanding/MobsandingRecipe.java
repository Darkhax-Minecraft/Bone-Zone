package net.skeletoncrew.bonezone.recipe.mobsanding;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.api.data.bytebuf.BookshelfByteBufs;
import net.darkhax.bookshelf.api.data.bytebuf.ByteBufHelper;
import net.darkhax.bookshelf.api.data.codecs.BookshelfCodecs;
import net.darkhax.bookshelf.api.data.codecs.CodecHelper;
import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.darkhax.bookshelf.api.registry.RegistryObject;
import net.darkhax.bookshelf.api.util.MathsHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.skeletoncrew.bonezone.Constants;

import java.util.Set;

public class MobsandingRecipe extends AbstractMobsandingRecipe {

    private static final CachedSupplier<RecipeSerializer<?>> SERIALIZER = RegistryObject.deferred(BuiltInRegistries.RECIPE_SERIALIZER, Constants.MOD_ID, "mobsanding").cast();

    private final Set<EntityType<?>> targetTypes;
    private final DropEntry[] drops;

    public MobsandingRecipe(Set<EntityType<?>> targetTypes, DropEntry[] drops) {

        this.targetTypes = targetTypes;
        this.drops = drops;
    }

    @Override
    public boolean canCraft(Level worldLevel, BlockPos pos, Entity target) {

        return targetTypes.contains(target.getType());
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

    public Set<EntityType<?>> getMobs() {
        return this.targetTypes;
    }

    public DropEntry[] getDrops() {
        return this.drops;
    }

    public static class DropEntry {

        public static final CodecHelper<DropEntry> DROP_CODEC = new CodecHelper<>(RecordCodecBuilder.create(instance -> instance.group(
                BookshelfCodecs.ITEM_STACK_FLEXIBLE.get("result", DropEntry::getDrop),
                BookshelfCodecs.FLOAT.get("chance", DropEntry::getChance, 1f)
        ).apply(instance, DropEntry::new)));
        public static final ByteBufHelper<DropEntry> DROP_BUFFER = new ByteBufHelper<>(DropEntry::read, DropEntry::write);

        public ItemStack item;
        public float chance;

        public DropEntry(ItemStack item, float chance) {

            this.item = item;
            this.chance = chance;
        }

        public ItemStack getDrop() {
            return this.item;
        }

        public float getChance() {
            return this.chance;
        }

        private static void write(FriendlyByteBuf buffer, DropEntry toWrite) {

            BookshelfByteBufs.ITEM_STACK.write(buffer, toWrite.getDrop());
            BookshelfByteBufs.FLOAT.write(buffer, toWrite.getChance());
        }

        private static DropEntry read(FriendlyByteBuf buffer) {

            final ItemStack stack = BookshelfByteBufs.ITEM_STACK.read(buffer);
            final float chance = BookshelfByteBufs.FLOAT.read(buffer);
            return new DropEntry(stack, chance);
        }
    }
}