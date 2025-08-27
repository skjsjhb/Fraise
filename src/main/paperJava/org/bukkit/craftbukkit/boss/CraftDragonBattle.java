package org.bukkit.craftbukkit.boss;

import kotlin.NotImplementedError;
import net.minecraft.world.level.dimension.end.DragonRespawnAnimation;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;

import java.util.Collection;
import java.util.List;

public class CraftDragonBattle implements DragonBattle {

    private final EndDragonFight handle;

    public CraftDragonBattle(EndDragonFight handle) {
        this.handle = handle;
    }

    @Override
    public EnderDragon getEnderDragon() {
        throw new NotImplementedError();
        // Entity entity = this.handle.level.getEntity(this.handle.dragonUUID);
        // return (entity != null) ? (EnderDragon) entity.getBukkitEntity() : null;
    }

    @Override
    public BossBar getBossBar() {
        throw new NotImplementedError();
        // return new CraftBossBar(this.handle.dragonEvent);
    }

    @Override
    public Location getEndPortalLocation() {
        throw new NotImplementedError();
        // if (this.handle.portalLocation == null) {
        //     return null;
        // }
        //
        // return CraftLocation.toBukkit(this.handle.portalLocation, this.handle.level.getWorld());
    }

    @Override
    public boolean generateEndPortal(boolean withPortals) {
        throw new NotImplementedError();
        // if (this.handle.portalLocation != null || this.handle.findExitPortal() != null) {
        //     return false;
        // }
        //
        // this.handle.spawnExitPortal(withPortals);
        // return true;
    }

    @Override
    public boolean hasBeenPreviouslyKilled() {
        return this.handle.hasPreviouslyKilledDragon();
    }

    @Override
    public void setPreviouslyKilled(boolean previouslyKilled) {
        throw new NotImplementedError();
        // this.handle.previouslyKilled = previouslyKilled;
    }

    @Override
    public void initiateRespawn() {
        this.handle.tryRespawn();
    }

    @Override
    public boolean initiateRespawn(Collection<EnderCrystal> list) {
        throw new NotImplementedError();
        // if (this.hasBeenPreviouslyKilled() && this.getRespawnPhase() == RespawnPhase.NONE) {
        //     // Copy from EndDragonFight#tryRespawn for generate exit portal if not exists
        //     if (this.handle.portalLocation == null) {
        //         BlockPattern.BlockPatternMatch patternMatch = this.handle.findExitPortal();
        //         if (patternMatch == null) {
        //             this.handle.spawnExitPortal(true);
        //         }
        //     }
        //
        //     list = (list != null) ? new ArrayList<>(list) : Collections.emptyList();
        //     list.removeIf(enderCrystal -> {
        //         if (enderCrystal == null) {
        //             return true;
        //         }
        //
        //         World world = enderCrystal.getWorld();
        //         return !((CraftWorld) world).getHandle().equals(this.handle.level);
        //     });
        //
        //     return this.handle.respawnDragon(list.stream().map(enderCrystal -> ((CraftEnderCrystal) enderCrystal).getHandle()).collect(Collectors.toList()));
        // }
        // return false;
    }

    @Override
    public RespawnPhase getRespawnPhase() {
        throw new NotImplementedError();
        // return this.toBukkitRespawnPhase(this.handle.respawnStage);
    }

    @Override
    public boolean setRespawnPhase(RespawnPhase phase) {
        throw new NotImplementedError();
        // Preconditions.checkArgument(phase != null && phase != RespawnPhase.NONE, "Invalid respawn phase provided: %s", phase);
        //
        // if (this.handle.respawnStage == null) {
        //     return false;
        // }
        //
        // this.handle.setRespawnStage(this.toNMSRespawnPhase(phase));
        // return true;
    }

    @Override
    public void resetCrystals() {
        this.handle.resetSpikeCrystals();
    }

    @Override
    public int hashCode() {
        return this.handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CraftDragonBattle && ((CraftDragonBattle) obj).handle == this.handle;
    }

    private RespawnPhase toBukkitRespawnPhase(DragonRespawnAnimation phase) {
        return (phase != null) ? RespawnPhase.values()[phase.ordinal()] : RespawnPhase.NONE;
    }

    private DragonRespawnAnimation toNMSRespawnPhase(RespawnPhase phase) {
        return (phase != RespawnPhase.NONE) ? DragonRespawnAnimation.values()[phase.ordinal()] : null;
    }

    @Override
    public int getGatewayCount() {
        throw new NotImplementedError();
        // return EndDragonFight.GATEWAY_COUNT - this.handle.gateways.size();
    }

    @Override
    public boolean spawnNewGateway() {
        throw new NotImplementedError();
        // return this.handle.spawnNewGatewayIfPossible();
    }

    @Override
    public void spawnNewGateway(final io.papermc.paper.math.Position position) {
        throw new NotImplementedError();
        // this.handle.spawnNewGateway(io.papermc.paper.util.MCUtil.toBlockPos(position));
    }

    @Override
    public List<org.bukkit.entity.EnderCrystal> getRespawnCrystals() {
        throw new NotImplementedError();
        // if (this.handle.respawnCrystals == null) {
        //     return Collections.emptyList();
        // }
        //
        // final List<EnderCrystal> enderCrystals = new ArrayList<>();
        // for (final net.minecraft.world.entity.boss.enderdragon.EndCrystal endCrystal : this.handle.respawnCrystals) {
        //     if (!endCrystal.isRemoved() && endCrystal.isAlive() && endCrystal.valid) {
        //         enderCrystals.add(((EnderCrystal) endCrystal.getBukkitEntity()));
        //     }
        // }
        // return Collections.unmodifiableList(enderCrystals);
    }

    @Override
    public List<EnderCrystal> getHealingCrystals() {
        throw new NotImplementedError();
        // final List<EnderCrystal> enderCrystals = new ArrayList<>();
        // for (final net.minecraft.world.entity.boss.enderdragon.EndCrystal endCrystal : this.handle.getSpikeCrystals()) {
        //     if (!endCrystal.isRemoved() && endCrystal.isAlive() && endCrystal.valid) {
        //         enderCrystals.add(((EnderCrystal) endCrystal.getBukkitEntity()));
        //     }
        // }
        // return Collections.unmodifiableList(enderCrystals);
    }
}
