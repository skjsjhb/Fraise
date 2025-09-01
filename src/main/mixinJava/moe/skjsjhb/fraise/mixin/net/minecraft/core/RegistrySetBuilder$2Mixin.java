package moe.skjsjhb.fraise.mixin.net.minecraft.core;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup$RegistryLookupExt;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(targets = "net.minecraft.core.RegistrySetBuilder$2")
public abstract class RegistrySetBuilder$2Mixin<T> implements HolderLookup$RegistryLookupExt<T> {
    @Shadow
    public abstract Optional<Holder.Reference<T>> get(ResourceKey<T> resourceKey);

    @Override
    public @NotNull Optional<T> getValueForCopying(@NotNull ResourceKey<T> key) {
        return get(key).map(Holder.Reference::value);
    }
}
