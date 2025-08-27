package io.papermc.paper.datacomponent.item;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.util.Handleable;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record PaperItemContainerContents(
    net.minecraft.world.item.component.ItemContainerContents impl
) implements ItemContainerContents, Handleable<net.minecraft.world.item.component.ItemContainerContents> {

    @Override
    public net.minecraft.world.item.component.ItemContainerContents getHandle() {
        return this.impl;
    }

    @Override
    public List<ItemStack> contents() {
        throw new NotImplementedError();
        // return MCUtil.transformUnmodifiable(this.impl.items, CraftItemStack::asBukkitCopy);
    }

    static final class BuilderImpl implements ItemContainerContents.Builder {

        private final List<net.minecraft.world.item.ItemStack> items = new ObjectArrayList<>();

        @Override
        public ItemContainerContents.Builder add(final ItemStack stack) {
            throw new NotImplementedError();
            // Preconditions.checkArgument(stack != null, "Item cannot be null");
            // Preconditions.checkArgument(
            //     this.items.size() + 1 <= net.minecraft.world.item.component.ItemContainerContents.MAX_SIZE,
            //     "Cannot have more than %s items, had %s",
            //     net.minecraft.world.item.component.ItemContainerContents.MAX_SIZE,
            //     this.items.size() + 1
            // );
            // this.items.add(CraftItemStack.asNMSCopy(stack));
            // return this;
        }

        @Override
        public ItemContainerContents.Builder addAll(final List<ItemStack> stacks) {
            throw new NotImplementedError();
            // Preconditions.checkArgument(
            //     this.items.size() + stacks.size() <= net.minecraft.world.item.component.ItemContainerContents.MAX_SIZE,
            //     "Cannot have more than %s items, had %s",
            //     net.minecraft.world.item.component.ItemContainerContents.MAX_SIZE,
            //     this.items.size() + stacks.size()
            // );
            // MCUtil.addAndConvert(this.items, stacks, stack -> {
            //     Preconditions.checkArgument(stack != null, "Cannot pass null item!");
            //     return CraftItemStack.asNMSCopy(stack);
            // });
            // return this;
        }

        @Override
        public ItemContainerContents build() {
            if (this.items.isEmpty()) {
                return new PaperItemContainerContents(net.minecraft.world.item.component.ItemContainerContents.EMPTY);
            }
            return new PaperItemContainerContents(net.minecraft.world.item.component.ItemContainerContents.fromItems(this.items));
        }
    }
}
