package org.bukkit.craftbukkit.inventory;

import kotlin.NotImplementedError;
import net.minecraft.world.item.crafting.CustomRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;

public class CraftComplexRecipe extends CraftingRecipe implements CraftRecipe, ComplexRecipe {

    private final CustomRecipe recipe;

    public CraftComplexRecipe(NamespacedKey key, ItemStack result, CustomRecipe recipe) {
        super(key, result);
        this.recipe = recipe;
    }

    @Override
    public void addToCraftingManager() {
        // MinecraftServer.getServer().getRecipeManager().addRecipe(new RecipeHolder<>(CraftRecipe.toMinecraft(this.getKey()), this.recipe));
        throw new NotImplementedError();
    }
}
