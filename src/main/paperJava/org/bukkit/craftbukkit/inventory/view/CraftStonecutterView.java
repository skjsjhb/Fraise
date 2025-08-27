package org.bukkit.craftbukkit.inventory.view;

import kotlin.NotImplementedError;
import net.minecraft.world.inventory.StonecutterMenu;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.StonecutterInventory;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.inventory.view.StonecutterView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CraftStonecutterView extends CraftInventoryView<StonecutterMenu, StonecutterInventory> implements StonecutterView {

    public CraftStonecutterView(final HumanEntity player, final StonecutterInventory viewing, final StonecutterMenu container) {
        super(player, viewing, container);
    }

    @Override
    public int getSelectedRecipeIndex() {
        return this.container.getSelectedRecipeIndex();
    }

    @NotNull
    @Override
    public List<StonecuttingRecipe> getRecipes() {
        throw new NotImplementedError();
        // final List<StonecuttingRecipe> recipes = new ArrayList<>();
        // for (final SelectableRecipe.SingleInputEntry<StonecutterRecipe> recipe : this.container.getVisibleRecipes().entries()) {
        //     recipe.recipe().recipe().map(RecipeHolder::toBukkitRecipe).ifPresent((bukkit) -> recipes.add((StonecuttingRecipe) bukkit));
        // }
        // return recipes;
    }

    @Override
    public int getRecipeAmount() {
        return this.container.getNumberOfVisibleRecipes();
    }
}
