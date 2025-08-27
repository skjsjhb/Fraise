package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import net.minecraft.core.BlockPos;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.entity.Vex;

public class CraftVex extends CraftMonster implements Vex {

    public CraftVex(CraftServer server, net.minecraft.world.entity.monster.Vex entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.monster.Vex getHandle() {
        return (net.minecraft.world.entity.monster.Vex) this.entity;
    }

    @Override
    public org.bukkit.entity.Mob getSummoner() {
        net.minecraft.world.entity.Mob owner = this.getHandle().getOwner();
        // return owner != null ? (org.bukkit.entity.Mob) owner.getBukkitEntity() : null;
        throw new NotImplementedError();
    }

    @Override
    public void setSummoner(org.bukkit.entity.Mob summoner) {
        // this.getHandle().owner = summoner == null ? null : new EntityReference<>(((CraftMob) summoner).getHandle());
        throw new NotImplementedError();
    }

    @Override
    public boolean hasLimitedLifetime() {
        // return this.getHandle().hasLimitedLife;
        throw new NotImplementedError();
    }

    @Override
    public void setLimitedLifetime(boolean hasLimitedLifetime) {
        // this.getHandle().hasLimitedLife = hasLimitedLifetime;
        throw new NotImplementedError();
    }

    @Override
    public int getLimitedLifetimeTicks() {
        // return this.getHandle().limitedLifeTicks;
        throw new NotImplementedError();
    }

    @Override
    public void setLimitedLifetimeTicks(int ticks) {
        // this.getHandle().limitedLifeTicks = ticks;
        throw new NotImplementedError();
    }

    @Override
    public boolean isCharging() {
        return this.getHandle().isCharging();
    }

    @Override
    public void setCharging(boolean charging) {
        this.getHandle().setIsCharging(charging);
    }

    @Override
    public Location getBound() {
        BlockPos pos = this.getHandle().getBoundOrigin();
        return (pos == null) ? null : CraftLocation.toBukkit(pos, this.getWorld());
    }

    @Override
    public void setBound(Location location) {
        if (location == null) {
            this.getHandle().setBoundOrigin(null);
        } else {
            Preconditions.checkArgument(this.getWorld().equals(location.getWorld()), "The bound world cannot be different to the entity's world.");
            this.getHandle().setBoundOrigin(CraftLocation.toBlockPosition(location));
        }
    }

    @Override
    public int getLifeTicks() {
        // return this.getHandle().limitedLifeTicks;
        throw new NotImplementedError();
    }

    @Override
    public void setLifeTicks(int lifeTicks) {
        // this.getHandle().setLimitedLife(lifeTicks);
        // if (lifeTicks < 0) {
        //     this.getHandle().hasLimitedLife = false;
        // }
        throw new NotImplementedError();
    }

    @Override
    public boolean hasLimitedLife() {
        // return this.getHandle().hasLimitedLife;
        throw new NotImplementedError();
    }
}
