package org.bukkit.craftbukkit.inventory;

import kotlin.NotImplementedError;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;
import java.util.Map;

public class RecipeIterator implements Iterator<Recipe> {
    private final Iterator<Map.Entry<RecipeType<?>, RecipeHolder<?>>> recipes;
    private final RecipeHolder<?> currentRecipe;

    public RecipeIterator() {
        this.recipes = null; // MinecraftServer.getServer().getRecipeManager().recipes.byType.entries().iterator();
        throw new NotImplementedError();
    }

    @Override
    public boolean hasNext() {
        return this.recipes.hasNext();
    }

    @Override
    public Recipe next() {
        // this.currentRecipe = this.recipes.next().getValue();
        // return this.currentRecipe.toBukkitRecipe();
        throw new NotImplementedError();
    }


    @Override
    public void remove() {
        throw new NotImplementedError();
        // MinecraftServer.getServer().getRecipeManager().recipes.byKey.remove(this.currentRecipe.id());
        // this.recipes.remove();
        // MinecraftServer.getServer().getRecipeManager().finalizeRecipeLoading();
        // MinecraftServer.getServer().getPlayerList().reloadRecipes();
    }
}
