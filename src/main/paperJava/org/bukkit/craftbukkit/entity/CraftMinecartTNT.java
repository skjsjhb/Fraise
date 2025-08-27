package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.minecart.ExplosiveMinecart;

public class CraftMinecartTNT extends CraftMinecart implements ExplosiveMinecart {

    public CraftMinecartTNT(CraftServer server, MinecartTNT entity) {
        super(server, entity);
    }

    @Override
    public MinecartTNT getHandle() {
        return (MinecartTNT) this.entity;
    }

    @Override
    public float getYield() {
        // return this.getHandle().explosionPowerBase;
        throw new NotImplementedError();
    }

    @Override
    public boolean isIncendiary() {
        // return this.getHandle().isIncendiary;
        throw new NotImplementedError();
    }

    @Override
    public void setIsIncendiary(boolean isIncendiary) {
        // this.getHandle().isIncendiary = isIncendiary;
        throw new NotImplementedError();
    }

    @Override
    public void setYield(float yield) {
        // this.getHandle().explosionPowerBase = yield;
        throw new NotImplementedError();
    }

    @Override
    public float getExplosionSpeedFactor() {
        // return this.getHandle().explosionSpeedFactor;
        throw new NotImplementedError();
    }

    @Override
    public void setExplosionSpeedFactor(float factor) {
        // this.getHandle().explosionSpeedFactor = factor;
        throw new NotImplementedError();
    }

    @Override
    public void setFuseTicks(int ticks) {
        // this.getHandle().fuse = ticks;
        throw new NotImplementedError();
    }

    @Override
    public int getFuseTicks() {
        return this.getHandle().getFuse();
    }

    @Override
    public void ignite() {
        this.getHandle().primeFuse(null);
    }

    @Override
    public boolean isIgnited() {
        return this.getHandle().isPrimed();
    }

    @Override
    public void explode() {
        // this.getHandle().explode(this.getHandle().getDeltaMovement().horizontalDistanceSqr());
        throw new NotImplementedError();
    }

    @Override
    public void explode(double power) {
        Preconditions.checkArgument(0 <= power && power <= Mth.square(5), "Power must be in range [0, 25] (got %s)", power);

        throw new NotImplementedError();
        // this.getHandle().explode(power);
    }
}
