package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Turtle;

public class CraftTurtle extends CraftAnimals implements Turtle {

    public CraftTurtle(CraftServer server, net.minecraft.world.entity.animal.Turtle entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.animal.Turtle getHandle() {
        return (net.minecraft.world.entity.animal.Turtle) this.entity;
    }

    @Override
    public boolean hasEgg() {
        return this.getHandle().hasEgg();
    }

    @Override
    public boolean isLayingEgg() {
        return this.getHandle().isLayingEgg();
    }

    @Override
    public org.bukkit.Location getHome() {
        // return CraftLocation.toBukkit(this.getHandle().homePos, this.getHandle().level());
        throw new NotImplementedError();
    }

    @Override
    public void setHome(org.bukkit.Location location) {
        // this.getHandle().homePos = CraftLocation.toBlockPosition(location);
        throw new NotImplementedError();
    }

    @Override
    public boolean isGoingHome() {
        // return this.getHandle().goingHome;
        throw new NotImplementedError();
    }

    @Override
    public void setHasEgg(boolean hasEgg) {
        // this.getHandle().setHasEgg(hasEgg);
        throw new NotImplementedError();
    }
}
