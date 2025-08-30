package moe.skjsjhb.fraise.mixin.net.minecraft.core;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup$RegistryLookupExt;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(targets = "net.minecraft.core.HolderLookup$RegistryLookup$1")
public abstract class HolderLookup$RegistryLookup$1Mixin<T> implements HolderLookup$RegistryLookupExt<T> {
    @Shadow
    public abstract HolderLookup.RegistryLookup<T> parent();

    @Shadow
    @Final
    Predicate<T> val$filter;

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Optional<T> getValueForCopying(@NotNull ResourceKey<T> key) {
        return ((HolderLookup$RegistryLookupExt<T>) parent()).getValueForCopying(key).filter(val$filter);
    }
}
