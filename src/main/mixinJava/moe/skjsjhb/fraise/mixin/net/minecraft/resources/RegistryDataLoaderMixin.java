package moe.skjsjhb.fraise.mixin.net.minecraft.resources;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.serialization.Decoder;
import io.papermc.paper.registry.PaperRegistryAccess;
import io.papermc.paper.registry.PaperRegistryListenerManager;
import io.papermc.paper.registry.data.util.Conversions;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Mixin(RegistryDataLoader.class)
public class RegistryDataLoaderMixin {
    @Inject(method = "createContext", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/RegistryDataLoader$1;<init>(Ljava/util/Map;)V"))
    private static void onCreateContext(
        List<HolderLookup.RegistryLookup<?>> registryLookups,
        List<RegistryDataLoader.Loader<?>> loaders,
        CallbackInfoReturnable<RegistryOps.RegistryInfoLookup> cir
    ) {
        RegistryDataLoader$1Ext.init$$providerForBuilders.set(
            HolderLookup.Provider.create(
                Stream.concat(registryLookups.stream(), loaders.stream().map(RegistryDataLoader.Loader::registry))
            )
        );
    }

    @Inject(method = "loadElementFromResource", at = @At("HEAD"))
    private static <E> void extractConversionsArg(
        WritableRegistry<E> registry,
        Decoder<E> codec,
        RegistryOps<JsonElement> ops,
        ResourceKey<E> resourceKey,
        Resource resource,
        RegistrationInfo registrationInfo,
        CallbackInfo ci,
        @Share("conversions") LocalRef<Conversions> conversions,
        @Share("hasConversions") LocalBooleanRef hasConversions
    ) {
        hasConversions.set(RegistryDataLoaderExt.loadElementFromResource$$conversions.maybeDump(conversions));
    }

    @Redirect(method = "loadElementFromResource", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/WritableRegistry;register(Lnet/minecraft/resources/ResourceKey;Ljava/lang/Object;Lnet/minecraft/core/RegistrationInfo;)Lnet/minecraft/core/Holder$Reference;"))
    private static <T> Holder.Reference<T> addConversions(
        WritableRegistry<T> instance,
        ResourceKey<T> tResourceKey,
        T t,
        RegistrationInfo registrationInfo,
        @Share("conversions") LocalRef<Conversions> conversions,
        @Share("hasConversions") LocalBooleanRef hasConversions
    ) {
        if (hasConversions.get()) {
            PaperRegistryListenerManager.INSTANCE.registerWithListeners(
                instance,
                tResourceKey,
                t,
                registrationInfo,
                conversions.get()
            );
            return null; // The return value is never used, so null is fine
        } else {
            return instance.register(tResourceKey, t, registrationInfo);
        }
    }

    @Inject(method = "loadContentsFromManager", at = @At("HEAD"))
    private static <E> void applyConversions(
        ResourceManager resourceManager,
        RegistryOps.RegistryInfoLookup registryInfoLookup,
        WritableRegistry<E> registry,
        Decoder<E> codec,
        Map<ResourceKey<?>, Exception> loadingErrors,
        CallbackInfo ci,
        @Share("conversions") LocalRef<Conversions> conversions
    ) {
        conversions.set(new Conversions(registryInfoLookup));
    }

    @Inject(method = "loadContentsFromManager", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/RegistryDataLoader;loadElementFromResource(Lnet/minecraft/core/WritableRegistry;Lcom/mojang/serialization/Decoder;Lnet/minecraft/resources/RegistryOps;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/server/packs/resources/Resource;Lnet/minecraft/core/RegistrationInfo;)V"))
    private static <E> void applyConversionsArg(
        ResourceManager resourceManager,
        RegistryOps.RegistryInfoLookup registryInfoLookup,
        WritableRegistry<E> registry,
        Decoder<E> codec,
        Map<ResourceKey<?>, Exception> loadingErrors,
        CallbackInfo ci,
        @Share("conversions") LocalRef<Conversions> conversions
    ) {
        RegistryDataLoaderExt.loadElementFromResource$$conversions.set(conversions.get());
    }

    @Inject(method = "loadContentsFromManager", at = @At(value = "INVOKE", target = "Lnet/minecraft/tags/TagLoader;loadTagsForRegistry(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/core/WritableRegistry;)V"))
    private static <E> void addTagLoaderCause(
        ResourceManager resourceManager,
        RegistryOps.RegistryInfoLookup registryInfoLookup,
        WritableRegistry<E> registry,
        Decoder<E> codec,
        Map<ResourceKey<?>, Exception> loadingErrors,
        CallbackInfo ci,
        @Share("conversions") LocalRef<Conversions> conversions
    ) {
        PaperRegistryAccess.instance().lockReferenceHolders(registry.key());
        PaperRegistryListenerManager.INSTANCE.runFreezeListeners(registry.key(), conversions.get());
        // FIXME: Extend the argument for `TagLoader.loadTagsForRegistry` (add cause)
    }

    @Inject(method = "loadContentsFromNetwork", at = @At("HEAD"))
    private static <E> void applyConversions(
        Map<ResourceKey<? extends Registry<?>>, RegistryDataLoader.NetworkedRegistryData> elements,
        ResourceProvider resourceProvider,
        RegistryOps.RegistryInfoLookup registryInfoLookup,
        WritableRegistry<E> registry,
        Decoder<E> codec,
        Map<ResourceKey<?>, Exception> loadingErrors,
        CallbackInfo ci,
        @Share("conversions") LocalRef<Conversions> conversions
    ) {
        conversions.set(new Conversions(registryInfoLookup));
    }

    @Inject(method = "loadContentsFromNetwork", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/RegistryDataLoader;loadElementFromResource(Lnet/minecraft/core/WritableRegistry;Lcom/mojang/serialization/Decoder;Lnet/minecraft/resources/RegistryOps;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/server/packs/resources/Resource;Lnet/minecraft/core/RegistrationInfo;)V"))
    private static <E> void applyConversionsArg(
        Map<ResourceKey<? extends Registry<?>>, RegistryDataLoader.NetworkedRegistryData> elements,
        ResourceProvider resourceProvider,
        RegistryOps.RegistryInfoLookup registryInfoLookup,
        WritableRegistry<E> registry,
        Decoder<E> codec,
        Map<ResourceKey<?>, Exception> loadingErrors,
        CallbackInfo ci,
        @Share("conversions") LocalRef<Conversions> conversions
    ) {
        RegistryDataLoaderExt.loadElementFromResource$$conversions.set(conversions.get());
    }
}
