package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Hoglin;

public class CraftHoglin extends CraftAnimals implements Hoglin, CraftEnemy {

    public CraftHoglin(CraftServer server, net.minecraft.world.entity.monster.hoglin.Hoglin entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.monster.hoglin.Hoglin getHandle() {
        return (net.minecraft.world.entity.monster.hoglin.Hoglin) this.entity;
    }

    @Override
    public boolean isImmuneToZombification() {
        throw new NotImplementedError();
        // return this.getHandle().isImmuneToZombification();
    }

    @Override
    public void setImmuneToZombification(boolean flag) {
        this.getHandle().setImmuneToZombification(flag);
    }

    @Override
    public boolean isAbleToBeHunted() {
        throw new NotImplementedError();
        // return this.getHandle().cannotBeHunted;
    }

    @Override
    public void setIsAbleToBeHunted(boolean flag) {
        throw new NotImplementedError();
        // this.getHandle().cannotBeHunted = flag;
    }

    @Override
    public int getConversionTime() {
        Preconditions.checkState(this.isConverting(), "Entity not converting");
        throw new NotImplementedError();
        // return this.getHandle().timeInOverworld;
    }

    @Override
    public void setConversionTime(int time) {
        throw new NotImplementedError();
        // if (time < 0) {
        //     this.getHandle().timeInOverworld = -1;
        //     this.getHandle().setImmuneToZombification(false);
        // } else {
        //     this.getHandle().timeInOverworld = time;
        // }
    }

    @Override
    public boolean isConverting() {
        return this.getHandle().isConverting();
    }
}
