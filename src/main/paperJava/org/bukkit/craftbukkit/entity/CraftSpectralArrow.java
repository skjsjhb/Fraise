package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.SpectralArrow;

public class CraftSpectralArrow extends CraftAbstractArrow implements SpectralArrow {

    public CraftSpectralArrow(CraftServer server, net.minecraft.world.entity.projectile.SpectralArrow entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.projectile.SpectralArrow getHandle() {
        return (net.minecraft.world.entity.projectile.SpectralArrow) this.entity;
    }

    @Override
    public int getGlowingTicks() {
        // return this.getHandle().duration;
        throw new NotImplementedError();
    }

    @Override
    public void setGlowingTicks(int duration) {
        // this.getHandle().duration = duration;
        throw new NotImplementedError();
    }
}
