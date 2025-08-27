package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import net.minecraft.world.entity.animal.allay.Allay;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.inventory.Inventory;

public class CraftAllay extends CraftCreature implements org.bukkit.entity.Allay {

    public CraftAllay(CraftServer server, Allay entity) {
        super(server, entity);
    }

    @Override
    public Allay getHandle() {
        return (Allay) this.entity;
    }

    @Override
    public Inventory getInventory() {
        return new CraftInventory(this.getHandle().getInventory());
    }

    @Override
    public boolean canDuplicate() {
        throw new NotImplementedError();
        // return this.getHandle().canDuplicate();
    }

    @Override
    public void setCanDuplicate(boolean canDuplicate) {
        throw new NotImplementedError();
        // this.getHandle().setCanDuplicate(canDuplicate);
    }

    @Override
    public long getDuplicationCooldown() {
        throw new NotImplementedError();
        // return this.getHandle().duplicationCooldown;
    }

    @Override
    public void setDuplicationCooldown(long l) {
        throw new NotImplementedError();
        // this.getHandle().duplicationCooldown = l;
    }

    @Override
    public void resetDuplicationCooldown() {
        throw new NotImplementedError();
        // this.getHandle().resetDuplicationCooldown();
    }

    @Override
    public boolean isDancing() {
        return this.getHandle().isDancing();
    }

    @Override
    public void startDancing(Location location) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(location.getBlock().getType().equals(Material.JUKEBOX), "The Block in the Location need to be a JukeBox");
        this.getHandle().setJukeboxPlaying(CraftLocation.toBlockPosition(location), true);
    }

    @Override
    public void startDancing() {
        throw new NotImplementedError();
        // this.getHandle().forceDancing = true;
        // this.getHandle().setDancing(true);
    }

    @Override
    public void stopDancing() {
        throw new NotImplementedError();
        // this.getHandle().forceDancing = false;
        // this.getHandle().jukeboxPos = null;
        // this.getHandle().setDancing(false); // Paper - Directly modify set dancing to avoid NPE
    }

    @Override
    public org.bukkit.entity.Allay duplicateAllay() {
        throw new NotImplementedError();
        // Allay nmsAllay = this.getHandle().duplicateAllay();
        // return (nmsAllay != null) ? (org.bukkit.entity.Allay) nmsAllay.getBukkitEntity() : null;
    }

    public Location getJukebox() {
        throw new NotImplementedError();
        // BlockPos nmsJukeboxPos = this.getHandle().jukeboxPos;
        // return (nmsJukeboxPos != null) ? CraftLocation.toBukkit(nmsJukeboxPos, this.getWorld()) : null;
    }
}
