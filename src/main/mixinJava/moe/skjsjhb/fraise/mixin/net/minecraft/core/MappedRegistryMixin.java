package moe.skjsjhb.fraise.mixin.net.minecraft.core;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> implements HolderLookup$RegistryLookupExt<T>, WritableRegistry<T>, MappedRegistryExt<T> {
    @Shadow
    private boolean frozen;
    @Shadow
    private @Nullable Map<T, Holder.Reference<T>> unregisteredIntrusiveHolders;
    @Unique
    private final Map<ResourceLocation, T> temporaryUnfrozenMap = new HashMap<>();

    @Override
    public @NotNull Optional<T> getValueForCopying(@NotNull ResourceKey<T> key) {
        if (frozen) {
            return getOptional(key);
        } else {
            return Optional.ofNullable(temporaryUnfrozenMap.get(key.location()));
        }
    }

    @Inject(method = "register", at = @At("RETURN"))
    private void addTempMap(ResourceKey<T> key, T value, RegistrationInfo registrationInfo, CallbackInfoReturnable<Holder.Reference<T>> cir) {
        temporaryUnfrozenMap.put(key.location(), value);
    }

    @Definition(id = "frozen", field = "Lnet/minecraft/core/MappedRegistry;frozen:Z")
    @Expression("this.frozen = true")
    @Inject(method = "freeze", at = @At("MIXINEXTRAS:EXPRESSION"))
    private void clearTempMap(CallbackInfoReturnable<Registry<T>> cir) {
        temporaryUnfrozenMap.clear();
    }

    @Override
    public void clearIntrusiveHolder(T instance) {
        if (unregisteredIntrusiveHolders != null) {
            unregisteredIntrusiveHolders.remove(instance);
        }
    }
}
