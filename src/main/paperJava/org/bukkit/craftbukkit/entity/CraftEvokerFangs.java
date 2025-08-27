package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.LivingEntity;

public class CraftEvokerFangs extends CraftEntity implements EvokerFangs {

    public CraftEvokerFangs(CraftServer server, net.minecraft.world.entity.projectile.EvokerFangs entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.projectile.EvokerFangs getHandle() {
        return (net.minecraft.world.entity.projectile.EvokerFangs) this.entity;
    }

    @Override
    public LivingEntity getOwner() {
        net.minecraft.world.entity.LivingEntity owner = this.getHandle().getOwner();

        throw new NotImplementedError();
        // return (owner == null) ? null : (LivingEntity) owner.getBukkitEntity();
    }

    @Override
    public void setOwner(LivingEntity owner) {
        this.getHandle().setOwner(owner == null ? null : ((CraftLivingEntity) owner).getHandle());
    }

    @Override
    public int getAttackDelay() {
        throw new NotImplementedError();
        // return this.getHandle().warmupDelayTicks;
    }

    @Override
    public void setAttackDelay(int delay) {
        Preconditions.checkArgument(delay >= 0, "Delay must be positive");

        throw new NotImplementedError();
        // this.getHandle().warmupDelayTicks = delay;
    }
}
