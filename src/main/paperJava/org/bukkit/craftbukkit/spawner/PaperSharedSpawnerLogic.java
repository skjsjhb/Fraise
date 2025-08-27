package org.bukkit.craftbukkit.spawner;

import com.google.common.base.Preconditions;
import com.mojang.logging.LogUtils;
import kotlin.NotImplementedError;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.spawner.Spawner;
import org.slf4j.Logger;

/**
 * A common parent interface for both the {@link org.bukkit.craftbukkit.block.CraftCreatureSpawner} and minecart mob spawner.
 */
public interface PaperSharedSpawnerLogic extends Spawner {

    Logger LOGGER = LogUtils.getLogger();

    BaseSpawner getSpawner();

    Level getInternalWorld();

    RegistryAccess getRegistryAccess();

    BlockPos getInternalPosition();

    default boolean isActivated() {
        // return this.getSpawner().isNearPlayer(this.getInternalWorld(), this.getInternalPosition());
        throw new NotImplementedError();
    }

    default void resetTimer() {
        // this.getSpawner().delay(this.getInternalWorld(), this.getInternalPosition());
        throw new NotImplementedError();
    }

    default void setNextSpawnData(SpawnData spawnData) {
        // this.getSpawner().setNextSpawnData(this.getInternalWorld(), this.getInternalPosition(), spawnData);
        throw new NotImplementedError();
    }

    default void setSpawnedItem(final ItemStack itemStack) {
        Preconditions.checkArgument(itemStack != null && !itemStack.getType().isAir(), "spawners cannot spawn air");

        final net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(() -> getSpawner().toString(), LOGGER)) {
            throw new NotImplementedError();
            // TagValueOutput tagValueOutput = TagValueOutput.createWithContext(scopedCollector, this.getInternalWorld().registryAccess());
            // tagValueOutput.putString(Entity.TAG_ID, BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ITEM).toString());
            // tagValueOutput.store("Item", net.minecraft.world.item.ItemStack.CODEC, item);
            //
            // this.setNextSpawnData(
            //     new net.minecraft.world.level.SpawnData(
            //         tagValueOutput.buildResult(),
            //         java.util.Optional.empty(),
            //         Optional.ofNullable(this.getSpawner().nextSpawnData).flatMap(SpawnData::equipment)
            //     )
            // );
        }

    }
}
