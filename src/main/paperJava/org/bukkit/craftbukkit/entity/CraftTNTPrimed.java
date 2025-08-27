package org.bukkit.craftbukkit.entity;

import kotlin.NotImplementedError;
import net.minecraft.world.entity.item.PrimedTnt;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;

public class CraftTNTPrimed extends CraftEntity implements TNTPrimed {

    public CraftTNTPrimed(CraftServer server, PrimedTnt entity) {
        super(server, entity);
    }

    @Override
    public PrimedTnt getHandle() {
        return (PrimedTnt) this.entity;
    }

    @Override
    public float getYield() {
        // return this.getHandle().explosionPower;
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
        // this.getHandle().explosionPower = yield;
        throw new NotImplementedError();
    }

    @Override
    public int getFuseTicks() {
        return this.getHandle().getFuse();
    }

    @Override
    public void setFuseTicks(int fuseTicks) {
        this.getHandle().setFuse(fuseTicks);
    }

    @Override
    public Entity getSource() {
        net.minecraft.world.entity.LivingEntity source = this.getHandle().getOwner();

        // return (source != null) ? source.getBukkitEntity() : null;
        throw new NotImplementedError();
    }

    @Override
    public void setSource(Entity source) {
        throw new NotImplementedError();
        // if (source instanceof LivingEntity) {
        //     this.getHandle().owner = new EntityReference<>(((CraftLivingEntity) source).getHandle());
        // } else {
        //     this.getHandle().owner = null;
        // }
    }

    @Override
    public void setBlockData(org.bukkit.block.data.BlockData data) {
        com.google.common.base.Preconditions.checkArgument(data != null, "The visual block data of this tnt cannot be null. To reset it just set to the TNT default block data");
        this.getHandle().setBlockState(((org.bukkit.craftbukkit.block.data.CraftBlockData) data).getState());
    }

    @Override
    public org.bukkit.block.data.BlockData getBlockData() {
        return org.bukkit.craftbukkit.block.data.CraftBlockData.fromData(this.getHandle().getBlockState());
    }
}
