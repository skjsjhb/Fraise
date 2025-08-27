package org.bukkit.craftbukkit.block;

import kotlin.NotImplementedError;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

public abstract class CraftContainer<T extends BaseContainerBlockEntity> extends CraftBlockEntityState<T> implements Container {

    public CraftContainer(World world, T blockEntity) {
        super(world, blockEntity);
    }

    protected CraftContainer(CraftContainer<T> state, Location location) {
        super(state, location);
    }

    @Override
    public boolean isLocked() {
        throw new NotImplementedError();
        // return this.getSnapshot().lockKey != LockCode.NO_LOCK;
    }

    @Override
    public String getLock() {
        throw new NotImplementedError();
        // Optional<? extends Component> customName = this.getSnapshot().lockKey.predicate().components().exact().asPatch().get(DataComponents.CUSTOM_NAME);
        //
        // return (customName != null) ? customName.map(CraftChatMessage::fromComponent).orElse("") : "";
    }

    @Override
    public void setLock(String key) {
        throw new NotImplementedError();
        // if (key == null) {
        //     this.getSnapshot().lockKey = LockCode.NO_LOCK;
        // } else {
        //     DataComponentExactPredicate predicate = DataComponentExactPredicate.builder().expect(DataComponents.CUSTOM_NAME, CraftChatMessage.fromStringOrNull(key)).build();
        //     this.getSnapshot().lockKey = new LockCode(new ItemPredicate(Optional.empty(), MinMaxBounds.Ints.ANY, new DataComponentMatchers(predicate, Collections.emptyMap())));
        // }
    }

    @Override
    public void setLockItem(ItemStack key) {
        throw new NotImplementedError();
        // if (key == null) {
        //     this.getSnapshot().lockKey = LockCode.NO_LOCK;
        // } else {
        //     this.getSnapshot().lockKey = new LockCode(CraftItemStack.asCriterionConditionItem(key));
        // }
    }

    @Override
    public net.kyori.adventure.text.Component customName() {
        final T blockEntity = this.getSnapshot();
        return blockEntity.hasCustomName() ? io.papermc.paper.adventure.PaperAdventure.asAdventure(blockEntity.getCustomName()) : null;
    }

    @Override
    public void customName(final net.kyori.adventure.text.Component customName) {
        // this.getSnapshot().name = customName != null ? io.papermc.paper.adventure.PaperAdventure.asVanilla(customName) : null;
        throw new NotImplementedError();
    }

    @Override
    public String getCustomName() {
        // T container = this.getSnapshot();
        // return container.name != null ? CraftChatMessage.fromComponent(container.getCustomName()) : null;
        throw new NotImplementedError();
    }

    @Override
    public void setCustomName(String name) {
        // this.getSnapshot().name = CraftChatMessage.fromStringOrNull(name);
        throw new NotImplementedError();
    }

    @Override
    public void applyTo(T blockEntity) {
        throw new NotImplementedError();
        // super.applyTo(blockEntity);
        //
        // if (this.getSnapshot().name == null) {
        //     blockEntity.name = null;
        // }
    }

    @Override
    public abstract CraftContainer<T> copy();

    @Override
    public abstract CraftContainer<T> copy(Location location);
}
