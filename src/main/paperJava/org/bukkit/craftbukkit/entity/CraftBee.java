package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import net.minecraft.core.BlockPos;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.entity.Bee;

public class CraftBee extends CraftAnimals implements Bee {

    public CraftBee(CraftServer server, net.minecraft.world.entity.animal.Bee entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.animal.Bee getHandle() {
        return (net.minecraft.world.entity.animal.Bee) this.entity;
    }

    @Override
    public Location getHive() {
        BlockPos hive = this.getHandle().getHivePos();
        return (hive == null) ? null : CraftLocation.toBukkit(hive, this.getWorld());
    }

    @Override
    public void setHive(Location location) {
        throw new NotImplementedError();
        // Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Hive must be in same world");
        // this.getHandle().hivePos = (location == null) ? null : CraftLocation.toBlockPosition(location);
    }

    @Override
    public Location getFlower() {
        BlockPos flower = this.getHandle().getSavedFlowerPos();
        return (flower == null) ? null : CraftLocation.toBukkit(flower, this.getWorld());
    }

    @Override
    public void setFlower(Location location) {
        Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Flower must be in same world");
        this.getHandle().setSavedFlowerPos(location == null ? null : CraftLocation.toBlockPosition(location));
    }

    @Override
    public boolean hasNectar() {
        return this.getHandle().hasNectar();
    }

    @Override
    public void setHasNectar(boolean nectar) {
        throw new NotImplementedError();
        // this.getHandle().setHasNectar(nectar);
    }

    @Override
    public boolean hasStung() {
        return this.getHandle().hasStung();
    }

    @Override
    public void setHasStung(boolean stung) {
        throw new NotImplementedError();
        // this.getHandle().setHasStung(stung);
    }

    @Override
    public int getAnger() {
        return this.getHandle().getRemainingPersistentAngerTime();
    }

    @Override
    public void setAnger(int anger) {
        this.getHandle().setRemainingPersistentAngerTime(anger);
    }

    @Override
    public int getCannotEnterHiveTicks() {
        throw new NotImplementedError();
        // return this.getHandle().stayOutOfHiveCountdown;
    }

    @Override
    public void setCannotEnterHiveTicks(int ticks) {
        this.getHandle().setStayOutOfHiveCountdown(ticks);
    }

    @Override
    public void setRollingOverride(net.kyori.adventure.util.TriState rolling) {
        throw new NotImplementedError();
        // this.getHandle().rollingOverride = rolling;
        //
        // this.getHandle().setRolling(this.getHandle().isRolling()); // Refresh rolling state
    }

    @Override
    public boolean isRolling() {
        throw new NotImplementedError();
        // return this.getRollingOverride().toBooleanOrElse(this.getHandle().isRolling());
    }

    @Override
    public net.kyori.adventure.util.TriState getRollingOverride() {
        throw new NotImplementedError();
        // return this.getHandle().rollingOverride;
    }

    @Override
    public void setCropsGrownSincePollination(int crops) {
        throw new NotImplementedError();
        // this.getHandle().numCropsGrownSincePollination = crops;
    }

    @Override
    public int getCropsGrownSincePollination() {
        throw new NotImplementedError();
        // return this.getHandle().numCropsGrownSincePollination;
    }

    @Override
    public void setTicksSincePollination(int ticks) {
        throw new NotImplementedError();
        // this.getHandle().ticksWithoutNectarSinceExitingHive = ticks;
    }

    @Override
    public int getTicksSincePollination() {
        throw new NotImplementedError();
        // return this.getHandle().ticksWithoutNectarSinceExitingHive;
    }

    @Override
    public void setTimeSinceSting(int time) {
        Preconditions.checkArgument(time >= 0, "Time since sting cannot be negative");
        throw new NotImplementedError();
        // this.getHandle().timeSinceSting = time;
    }

    @Override
    public int getTimeSinceSting() {
        throw new NotImplementedError();
        // return this.getHandle().timeSinceSting;
    }
}
