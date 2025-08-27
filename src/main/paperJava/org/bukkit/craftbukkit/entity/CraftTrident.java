package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

public class CraftTrident extends CraftAbstractArrow implements Trident {

    public CraftTrident(CraftServer server, ThrownTrident entity) {
        super(server, entity);
    }

    @Override
    public ThrownTrident getHandle() {
        return (ThrownTrident) this.entity;
    }

    @Override
    public ItemStack getItem() {
        // return CraftItemStack.asBukkitCopy(this.getHandle().pickupItemStack);
        throw new NotImplementedError();
    }

    @Override
    public void setItem(ItemStack itemStack) {
        // this.getHandle().pickupItemStack = CraftItemStack.asNMSCopy(itemStack);
        throw new NotImplementedError();
    }

    @Override
    public boolean hasGlint() {
        return this.getHandle().isFoil();
    }

    @Override
    public void setGlint(boolean glint) {
        // this.getHandle().setFoil(glint);
        throw new NotImplementedError();
    }

    @Override
    public int getLoyaltyLevel() {
        // return this.getHandle().getLoyalty();
        throw new NotImplementedError();
    }

    @Override
    public void setLoyaltyLevel(int loyaltyLevel) {
        throw new NotImplementedError();
        // com.google.common.base.Preconditions.checkArgument(loyaltyLevel >= 0 && loyaltyLevel <= 127, "The loyalty level has to be between 0 and 127");
        // this.getHandle().setLoyalty((byte) loyaltyLevel);
    }

    @Override
    public boolean hasDealtDamage() {
        // return this.getHandle().dealtDamage;
        throw new NotImplementedError();
    }

    @Override
    public void setHasDealtDamage(boolean hasDealtDamage) {
        // this.getHandle().dealtDamage = hasDealtDamage;
        throw new NotImplementedError();
    }
}
