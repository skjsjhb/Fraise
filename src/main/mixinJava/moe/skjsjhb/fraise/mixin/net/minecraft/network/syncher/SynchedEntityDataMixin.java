package moe.skjsjhb.fraise.mixin.net.minecraft.network.syncher;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityDataExt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(SynchedEntityData.class)
public abstract class SynchedEntityDataMixin implements SynchedEntityDataExt {
    @Shadow
    protected abstract <T> SynchedEntityData.DataItem<T> getItem(EntityDataAccessor<T> key);

    @Shadow
    private boolean isDirty;

    @Shadow
    @Final
    private SynchedEntityData.DataItem<?>[] itemsById;

    @Override
    public <T> void markDirty(EntityDataAccessor<T> da) {
        getItem(da).setDirty(true);
        isDirty = true;
    }

    @Override
    public List<SynchedEntityData.DataValue<?>> packAll() {
        final List<SynchedEntityData.DataValue<?>> list = new ArrayList<>(itemsById.length);
        for (var dataItem : itemsById) {
            list.add(dataItem.value());
        }
        return list;
    }
}
