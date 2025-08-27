package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import net.minecraft.world.entity.animal.frog.Tadpole;
import org.bukkit.craftbukkit.CraftServer;

public class CraftTadpole extends CraftFish implements org.bukkit.entity.Tadpole {

    public CraftTadpole(CraftServer server, Tadpole entity) {
        super(server, entity);
    }

    @Override
    public Tadpole getHandle() {
        return (Tadpole) this.entity;
    }

    @Override
    public int getAge() {
        // return this.getHandle().age;
        throw new NotImplementedError();
    }

    @Override
    public void setAge(int age) {
        // this.getHandle().age = age;
        throw new NotImplementedError();
    }

    @Override
    public void setAgeLock(boolean lock) {
        // this.getHandle().ageLocked = lock;
        throw new NotImplementedError();
    }

    @Override
    public boolean getAgeLock() {
        // return this.getHandle().ageLocked;
        throw new NotImplementedError();
    }
}
