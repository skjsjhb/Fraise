package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.ChestedHorse;

public abstract class CraftChestedHorse extends CraftAbstractHorse implements ChestedHorse {

    public CraftChestedHorse(CraftServer server, AbstractChestedHorse entity) {
        super(server, entity);
    }

    @Override
    public AbstractChestedHorse getHandle() {
        return (AbstractChestedHorse) this.entity;
    }

    @Override
    public boolean isCarryingChest() {
        return this.getHandle().hasChest();
    }

    @Override
    public void setCarryingChest(boolean chest) {
        if (chest == this.isCarryingChest()) return;
        this.getHandle().setChest(chest);
        throw new NotImplementedError();
        // this.getHandle().createInventory();
    }
}
