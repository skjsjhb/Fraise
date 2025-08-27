package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.ExperienceOrb;

import java.util.UUID;

public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb {

    public CraftExperienceOrb(CraftServer server, net.minecraft.world.entity.ExperienceOrb entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.ExperienceOrb getHandle() {
        return (net.minecraft.world.entity.ExperienceOrb) this.entity;
    }

    @Override
    public int getExperience() {
        return this.getHandle().getValue();
    }

    @Override
    public void setExperience(int value) {
        throw new NotImplementedError();
        // this.getHandle().setValue(value);
    }

    @Override
    public int getCount() {
        throw new NotImplementedError();
        // return this.getHandle().count;
    }

    @Override
    public void setCount(final int count) {
        throw new NotImplementedError();
        // this.getHandle().count = count;
    }

    @Override
    public UUID getTriggerEntityId() {
        throw new NotImplementedError();
        // return this.getHandle().triggerEntityId;
    }

    @Override
    public UUID getSourceEntityId() {
        throw new NotImplementedError();
        // return this.getHandle().sourceEntityId;
    }

    @Override
    public SpawnReason getSpawnReason() {
        throw new NotImplementedError();
        // return this.getHandle().spawnReason;
    }
}
