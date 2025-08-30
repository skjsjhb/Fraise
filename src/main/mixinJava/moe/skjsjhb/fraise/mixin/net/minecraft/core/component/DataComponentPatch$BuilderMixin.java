package moe.skjsjhb.fraise.mixin.net.minecraft.core.component;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentPatch$BuilderExt;
import net.minecraft.core.component.DataComponentType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(targets = "net.minecraft.core.component.DataComponentPatch$Builder")
public class DataComponentPatch$BuilderMixin implements DataComponentPatch$BuilderExt {
    @Shadow
    @Final
    public Reference2ObjectMap<DataComponentType<?>, Optional<?>> map;

    @Override
    public void copy(@NotNull DataComponentPatch orig) {
        map.putAll(orig.map);
    }

    @Override
    public void clear(@NotNull DataComponentType<?> type) {
        map.remove(type);
    }

    @Override
    public boolean isSet(@NotNull DataComponentType<?> type) {
        return map.containsKey(type);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof DataComponentPatch.Builder b) return map.equals(b.map);
        return false;
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
