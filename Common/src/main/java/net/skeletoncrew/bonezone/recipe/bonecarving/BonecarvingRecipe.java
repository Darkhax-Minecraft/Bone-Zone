package net.skeletoncrew.bonezone.recipe.bonecarving;

import net.darkhax.bookshelf.api.data.sound.Sound;
import net.darkhax.bookshelf.api.function.CachedSupplier;
import net.darkhax.bookshelf.api.registry.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.skeletoncrew.bonezone.Constants;

public class BonecarvingRecipe extends AbstractBonecarvingRecipe {

    private static final CachedSupplier<RecipeSerializer<?>> SERIALIZER = RegistryObject.deferred(Registry.RECIPE_SERIALIZER, Constants.MOD_ID, "bonecarving").cast();

    protected final Ingredient input;
    protected final ItemStack output;
    protected final Sound sound;

    public BonecarvingRecipe(ResourceLocation id, Ingredient input, ItemStack output, Sound sound) {

        super(id);
        this.input = input;
        this.output = output;
        this.sound = sound;
    }

    @Override
    public boolean canCraft(Player player, Level worldLevel, BlockPos pos, ItemStack inputStack) {

        return this.input.test(inputStack);
    }

    @Override
    public void playSound(Player player, Level worldLevel, BlockPos pos, ItemStack output) {

        this.sound.playSoundAt(worldLevel, null, pos);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return SERIALIZER.get();
    }

    @Override
    public ItemStack getResultItem() {

        return this.output;
    }

    @Override
    public ItemStack assemble(Container c) {

        return this.getResultItem().copy();
    }
}
