package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import kotlin.NotImplementedError;
import net.minecraft.world.item.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemType;
import org.bukkit.entity.Piglin;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class CraftPiglin extends CraftPiglinAbstract implements Piglin, com.destroystokyo.paper.entity.CraftRangedEntity<net.minecraft.world.entity.monster.piglin.Piglin> { // Paper

    public CraftPiglin(CraftServer server, net.minecraft.world.entity.monster.piglin.Piglin entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.monster.piglin.Piglin getHandle() {
        return (net.minecraft.world.entity.monster.piglin.Piglin) this.entity;
    }

    @Override
    public boolean isAbleToHunt() {
        throw new NotImplementedError();
        // return this.getHandle().cannotHunt;
    }

    @Override
    public void setIsAbleToHunt(boolean flag) {
        throw new NotImplementedError();
        // this.getHandle().cannotHunt = flag;
    }

    @Override
    public boolean addBarterMaterial(Material material) {
        Preconditions.checkArgument(material != null, "material cannot be null");

        Item item = CraftItemType.bukkitToMinecraft(material);
        throw new NotImplementedError();
        // return this.getHandle().allowedBarterItems.add(item);
    }

    @Override
    public boolean removeBarterMaterial(Material material) {
        Preconditions.checkArgument(material != null, "material cannot be null");

        Item item = CraftItemType.bukkitToMinecraft(material);
        throw new NotImplementedError();
        // return this.getHandle().allowedBarterItems.remove(item);
    }

    @Override
    public boolean addMaterialOfInterest(Material material) {
        Preconditions.checkArgument(material != null, "material cannot be null");

        Item item = CraftItemType.bukkitToMinecraft(material);
        throw new NotImplementedError();
        // return this.getHandle().interestItems.add(item);
    }

    @Override
    public boolean removeMaterialOfInterest(Material material) {
        Preconditions.checkArgument(material != null, "material cannot be null");

        Item item = CraftItemType.bukkitToMinecraft(material);
        throw new NotImplementedError();
        // return this.getHandle().interestItems.remove(item);
    }

    @Override
    public Set<Material> getInterestList() {
        throw new NotImplementedError();
        // return Collections.unmodifiableSet(this.getHandle().interestItems.stream().map(CraftItemType::minecraftToBukkit).collect(Collectors.toSet()));
    }

    @Override
    public Set<Material> getBarterList() {
        throw new NotImplementedError();
        // return Collections.unmodifiableSet(this.getHandle().allowedBarterItems.stream().map(CraftItemType::minecraftToBukkit).collect(Collectors.toSet()));
    }

    @Override
    public Inventory getInventory() {
        throw new NotImplementedError();
        // return new CraftInventory(this.getHandle().inventory);
    }

    @Override
    public void setChargingCrossbow(boolean chargingCrossbow) {
        this.getHandle().setChargingCrossbow(chargingCrossbow);
    }

    @Override
    public boolean isChargingCrossbow() {
        throw new NotImplementedError();
        // return this.getHandle().isChargingCrossbow();
    }

    @Override
    public void setDancing(boolean dancing) {
        if (dancing) {
            this.getHandle().getBrain().setMemory(net.minecraft.world.entity.ai.memory.MemoryModuleType.DANCING, true);
            this.getHandle().getBrain().setMemory(net.minecraft.world.entity.ai.memory.MemoryModuleType.CELEBRATE_LOCATION, this.getHandle().getOnPos());
        } else {
            this.getHandle().getBrain().eraseMemory(net.minecraft.world.entity.ai.memory.MemoryModuleType.DANCING);
            this.getHandle().getBrain().eraseMemory(net.minecraft.world.entity.ai.memory.MemoryModuleType.CELEBRATE_LOCATION);
        }
    }

    @Override
    public void setDancing(long duration) {
        this.getHandle().getBrain().setMemoryWithExpiry(net.minecraft.world.entity.ai.memory.MemoryModuleType.DANCING, true, duration);
        this.getHandle().getBrain().setMemoryWithExpiry(net.minecraft.world.entity.ai.memory.MemoryModuleType.CELEBRATE_LOCATION, this.getHandle().getOnPos(), duration);
    }

    @Override
    public boolean isDancing() {
        return this.getHandle().isDancing();
    }
}
