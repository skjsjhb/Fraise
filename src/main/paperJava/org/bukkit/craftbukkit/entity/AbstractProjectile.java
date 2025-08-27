package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Projectile;

public abstract class AbstractProjectile extends CraftEntity implements Projectile {

    public AbstractProjectile(CraftServer server, net.minecraft.world.entity.Entity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.projectile.Projectile getHandle() {
        return (net.minecraft.world.entity.projectile.Projectile) this.entity;
    }

    @Override
    public boolean doesBounce() {
        return false;
    }

    @Override
    public void setBounce(boolean doesBounce) {
    }

    // Paper start - More projectile API
    @Override
    public boolean hasLeftShooter() {
        throw new NotImplementedError();
        // return this.getHandle().leftOwner;
    }

    @Override
    public void setHasLeftShooter(boolean leftShooter) {
        throw new NotImplementedError();
        // this.getHandle().leftOwner = leftShooter;
    }

    @Override
    public boolean hasBeenShot() {
        throw new NotImplementedError();
        // return this.getHandle().hasBeenShot;
    }

    @Override
    public void setHasBeenShot(boolean beenShot) {
        throw new NotImplementedError();
        // this.getHandle().hasBeenShot = beenShot;
    }

    @Override
    public boolean canHitEntity(org.bukkit.entity.Entity entity) {
        throw new NotImplementedError();
        // return this.getHandle().canHitEntityPublic(((CraftEntity) entity).getHandle());
    }

    @Override
    public void hitEntity(org.bukkit.entity.Entity entity) {
        throw new NotImplementedError();
        // this.getHandle().preHitTargetOrDeflectSelf(new net.minecraft.world.phys.EntityHitResult(((CraftEntity) entity).getHandle()));
    }

    @Override
    public void hitEntity(org.bukkit.entity.Entity entity, org.bukkit.util.Vector vector) {
        throw new NotImplementedError();
        // this.getHandle().preHitTargetOrDeflectSelf(new net.minecraft.world.phys.EntityHitResult(((CraftEntity) entity).getHandle(), new net.minecraft.world.phys.Vec3(vector.getX(), vector.getY(), vector.getZ())));
    }

    @Override
    public final org.bukkit.projectiles.ProjectileSource getShooter() {
        throw new NotImplementedError();
        // this.getHandle().refreshProjectileSource(true); // Paper - Refresh ProjectileSource for projectiles
        // return this.getHandle().projectileSource;
    }

    @Override
    public final void setShooter(org.bukkit.projectiles.ProjectileSource shooter) {
        throw new NotImplementedError();
        // if (shooter instanceof CraftEntity craftEntity) {
        //     this.getHandle().setOwner(craftEntity.getHandle());
        // } else {
        //     this.getHandle().setOwner(null);
        // }
        // this.getHandle().projectileSource = shooter;
    }

    @Override
    public java.util.UUID getOwnerUniqueId() {
        throw new NotImplementedError();
        // return Optionull.map(this.getHandle().owner, EntityReference::getUUID);
    }
    // Paper end - More projectile API
}
