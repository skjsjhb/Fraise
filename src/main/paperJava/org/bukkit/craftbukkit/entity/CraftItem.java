package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import net.minecraft.world.entity.item.ItemEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CraftItem extends CraftEntity implements Item {

    private final static int NO_AGE_TIME = Short.MIN_VALUE; // ItemEntity#INFINITE_LIFETIME
    private final static int NO_PICKUP_TIME = Short.MAX_VALUE; // ItemEntity#INFINITE_PICKUP_DELAY

    public CraftItem(CraftServer server, ItemEntity entity) {
        super(server, entity);
    }

    @Override
    public ItemEntity getHandle() {
        return (ItemEntity) this.entity;
    }

    @Override
    public ItemStack getItemStack() {
        return CraftItemStack.asCraftMirror(this.getHandle().getItem());
    }

    @Override
    public void setItemStack(ItemStack stack) {
        this.getHandle().setItem(CraftItemStack.asNMSCopy(stack));
    }

    @Override
    public int getPickupDelay() {
        throw new NotImplementedError();
        // return this.getHandle().pickupDelay;
    }

    @Override
    public void setPickupDelay(int delay) {
        throw new NotImplementedError();
        // this.getHandle().pickupDelay = Math.min(delay, Short.MAX_VALUE);
    }

    @Override
    public void setUnlimitedLifetime(boolean unlimited) {
        throw new NotImplementedError();
        // if (unlimited) {
        //     // See EntityItem#INFINITE_LIFETIME
        //     this.getHandle().age = Short.MIN_VALUE;
        // } else {
        //     this.getHandle().age = this.getTicksLived();
        // }
    }

    @Override
    public boolean isUnlimitedLifetime() {
        throw new NotImplementedError();
        // return this.getHandle().age == Short.MIN_VALUE;
    }

    @Override
    public void setTicksLived(int value) {
        throw new NotImplementedError();
        // super.setTicksLived(value);
        //
        // // Second field for EntityItem (don't set if lifetime is unlimited)
        // if (!this.isUnlimitedLifetime()) {
        //     this.getHandle().age = value;
        // }
    }

    @Override
    public boolean canMobPickup() {
        throw new NotImplementedError();
        // return this.getHandle().canMobPickup;
    }

    @Override
    public void setCanMobPickup(boolean canMobPickup) {
        throw new NotImplementedError();
        // this.getHandle().canMobPickup = canMobPickup;
    }

    @Override
    public boolean canPlayerPickup() {
        throw new NotImplementedError();
        // return this.getHandle().pickupDelay != NO_PICKUP_TIME;
    }

    @Override
    public void setCanPlayerPickup(boolean canPlayerPickup) {
        throw new NotImplementedError();
        // this.getHandle().pickupDelay = canPlayerPickup ? 0 : NO_PICKUP_TIME;
    }

    @Override
    public boolean willAge() {
        throw new NotImplementedError();
        // return this.getHandle().age != NO_AGE_TIME;
    }

    @Override
    public void setWillAge(boolean willAge) {
        throw new NotImplementedError();
        // this.getHandle().age = willAge ? 0 : NO_AGE_TIME;
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public net.kyori.adventure.util.TriState getFrictionState() {
        throw new NotImplementedError();
        // return this.getHandle().frictionState;
    }

    @Override
    public void setFrictionState(@org.jetbrains.annotations.NotNull net.kyori.adventure.util.TriState state) {
        throw new NotImplementedError();
        // Preconditions.checkArgument(state != null, "state may not be null");
        // this.getHandle().frictionState = state;
    }

    @Override
    public int getHealth() {
        throw new NotImplementedError();
        // return this.getHandle().health;
    }

    @Override
    public void setHealth(int health) {
        throw new NotImplementedError();
        // if (health <= 0) {
        //     this.getHandle().getItem().onDestroyed(this.getHandle());
        //     this.getHandle().discard(org.bukkit.event.entity.EntityRemoveEvent.Cause.PLUGIN);
        // } else {
        //     this.getHandle().health = health;
        // }
    }

    @Override
    public void setOwner(UUID uuid) {
        this.getHandle().setTarget(uuid);
    }

    @Override
    public UUID getOwner() {
        throw new NotImplementedError();
        // return this.getHandle().target;
    }

    @Override
    public void setThrower(UUID uuid) {
        throw new NotImplementedError();
        // this.getHandle().thrower = uuid == null ? null : new EntityReference<>(uuid);
    }

    @Override
    public UUID getThrower() {
        throw new NotImplementedError();
        // return Optionull.map(this.getHandle().thrower, EntityReference::getUUID);
    }
}
