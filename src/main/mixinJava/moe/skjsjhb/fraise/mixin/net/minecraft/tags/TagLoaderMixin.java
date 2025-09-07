package moe.skjsjhb.fraise.mixin.net.minecraft.tags;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import io.papermc.paper.tag.PaperTagListenerManager;
import io.papermc.paper.tag.TagEventConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagLoaderExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Mixin(TagLoader.class)
public class TagLoaderMixin<T> implements TagLoaderExt<T> {
    @SuppressWarnings("unchecked")
    @Inject(method = "build", at = @At("HEAD"))
    private void applyEventConfig(
        Map<ResourceLocation, List<TagLoader.EntryWithSource>> builders,
        CallbackInfoReturnable<Map<ResourceLocation, List<T>>> cir,
        @Share("eventConfig") LocalRef<TagEventConfig<T, ?>> eventConfig,
        @Share("hasEventConfig") LocalBooleanRef hasEventConfig,
        @Local(argsOnly = true) LocalRef<Map<?, ?>> buildersRef
    ) {
        hasEventConfig.set(
            TagLoaderExt.build$$eventConfig.maybeDump((LocalRef<TagEventConfig<?, ?>>) (Object) eventConfig)
        );

        if (hasEventConfig.get()) {
            buildersRef.set(
                PaperTagListenerManager.INSTANCE.firePreFlattenEvent(builders, eventConfig.get())
            );
        }
    }

    @Inject(method = "build", at = @At("RETURN"), cancellable = true)
    private void firePostFlattenEvent(
        Map<ResourceLocation, List<TagLoader.EntryWithSource>> builders,
        CallbackInfoReturnable<Map<ResourceLocation, List<T>>> cir,
        @Share("eventConfig") LocalRef<TagEventConfig<T, ?>> eventConfig,
        @Share("hasEventConfig") LocalBooleanRef hasEventConfig
    ) {
        if (hasEventConfig.get()) {
            cir.setReturnValue(
                PaperTagListenerManager.INSTANCE.firePostFlattenEvent(cir.getReturnValue(), eventConfig.get())
            );
        }
    }

    @Inject(method = "loadTagsForExistingRegistries", at = @At("HEAD"))
    private static void addCause(
        ResourceManager resourceManager,
        RegistryAccess registryAccess,
        CallbackInfoReturnable<List<Registry.PendingTags<?>>> cir,
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause
    ) {
        if (TagLoaderExt.loadTagsForExistingRegistries$$cause.hasValue()) {
            cause.set(TagLoaderExt.loadTagsForExistingRegistries$$cause.get());
        } else {
            cause.set(ReloadableRegistrarEvent.Cause.INITIAL);
        }
    }

    @ModifyArg(method = "loadTagsForExistingRegistries", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;map(Ljava/util/function/Function;)Ljava/util/stream/Stream;"))
    private static <T, R> Function<T, R> passCause(
        Function<T, R> mapper, // method_61306
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause
    ) {
        var causeValue = cause.get();

        return (t) -> {
            TagLoaderExt.syn61306$$cause.set(causeValue);
            return mapper.apply(t);
        };
    }

    @Inject(method = "method_61306", at = @At("HEAD"))
    private static void addCause(
        ResourceManager resourceManager,
        RegistryAccess.RegistryEntry<?> registryEntry,
        CallbackInfoReturnable<Optional<?>> cir,
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause,
        @Share("hasCause") LocalBooleanRef hasCause
    ) {
        hasCause.set(TagLoaderExt.syn61306$$cause.maybeDump(cause));
    }

    @Inject(method = "method_61306", at = @At(value = "INVOKE", target = "Lnet/minecraft/tags/TagLoader;loadPendingTags(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/core/Registry;)Ljava/util/Optional;"))
    private static void passCause(
        ResourceManager resourceManager,
        RegistryAccess.RegistryEntry<?> registryEntry,
        CallbackInfoReturnable<Optional<?>> cir,
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause,
        @Share("hasCause") LocalBooleanRef hasCause
    ) {
        if (hasCause.get()) {
            TagLoaderExt.loadPendingTags$$cause.set(cause.get());
        }
    }

    @Inject(method = "loadTagsForRegistry", at = @At("HEAD"))
    private static void addCause(
        ResourceManager resourceManager,
        WritableRegistry<?> registry,
        CallbackInfo ci,
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause,
        @Share("hasCause") LocalBooleanRef hasCause
    ) {
        hasCause.set(TagLoaderExt.loadTagsForRegistry$$cause.maybeDump(cause));
    }

    @Inject(method = "loadTagsForRegistry", at = @At(value = "INVOKE", target = "Lnet/minecraft/tags/TagLoader;build(Ljava/util/Map;)Ljava/util/Map;"))
    private static void passCause(
        ResourceManager resourceManager,
        WritableRegistry<?> registry,
        CallbackInfo ci,
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause,
        @Share("hasCause") LocalBooleanRef hasCause
    ) {
        if (hasCause.get()) {
            TagLoaderExt.build$$eventConfig.set(
                PaperTagListenerManager.INSTANCE.createEventConfig(registry, cause.get())
            );
        }
    }

    @Inject(method = "loadPendingTags", at = @At("HEAD"))
    private static void addCause(
        ResourceManager resourceManager,
        Registry<?> registry,
        CallbackInfoReturnable<Optional<Registry.PendingTags<?>>> cir,
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause,
        @Share("hasCause") LocalBooleanRef hasCause
    ) {
        hasCause.set(TagLoaderExt.loadPendingTags$$cause.maybeDump(cause));
    }

    @Inject(method = "loadPendingTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/tags/TagLoader;build(Ljava/util/Map;)Ljava/util/Map;"))
    private static void passCause(
        ResourceManager resourceManager,
        Registry<?> registry,
        CallbackInfoReturnable<Optional<Registry.PendingTags<?>>> cir,
        @Share("cause") LocalRef<ReloadableRegistrarEvent.Cause> cause,
        @Share("hasCause") LocalBooleanRef hasCause
    ) {
        if (hasCause.get()) {
            TagLoaderExt.build$$eventConfig.set(
                PaperTagListenerManager.INSTANCE.createEventConfig(registry, cause.get())
            );
        }
    }
}
