package moe.skjsjhb.fraise.mixin.net.minecraft.core.registries;

import com.llamalad7.mixinextras.sugar.Local;
import io.papermc.paper.registry.PaperRegistryAccess;
import io.papermc.paper.registry.PaperRegistryListenerManager;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.BuiltInRegistriesExt;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Debug(export = true)
@Mixin(BuiltInRegistries.class)
public class BuiltInRegistriesMixin {
    @Shadow
    @Final
    public static Registry<? extends Registry<?>> REGISTRY;

    @Inject(method = "internalRegister", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/server/Bootstrap;checkBootstrapCalled(Ljava/util/function/Supplier;)V",
        shift = At.Shift.AFTER
    ))
    private static <T, R extends WritableRegistry<T>> void addToPaper(
        ResourceKey<? extends Registry<T>> key,
        R registry,
        BuiltInRegistries.RegistryBootstrap<T> bootstrap,
        CallbackInfoReturnable<R> cir
    ) {
        PaperRegistryAccess.instance().registerRegistry(registry);
    }

    @Inject(method = "bootStrap", at = @At("HEAD"))
    private static void freezeOnBoot(CallbackInfo ci) {
        REGISTRY.freeze(); // XXX: Figure out whether this is necessary
    }

    @Inject(method = "createContents", at = @At("HEAD"))
    private static void loadBukkitRegistry(CallbackInfo ci) {
        try {
            Class.forName(org.bukkit.Registry.class.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Inject(method = "method_47482", at = @At("RETURN")) // In `createContents`
    private static void lockReferenceHolder(ResourceLocation resourceLocation, Supplier<?> supplier, CallbackInfo ci) {
        PaperRegistryAccess.instance().lockReferenceHolders(ResourceKey.createRegistryKey(resourceLocation));
    }


    @SuppressWarnings({"UnresolvedLocalCapture", "LocalMayBeArgsOnly"}) // False positive
    @Inject(method = "freeze", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/core/Registry;freeze()Lnet/minecraft/core/Registry;"
    ))
    private static void onFreezeRegistry(CallbackInfo ci, @Local Registry<?> registry) {
        PaperRegistryListenerManager.INSTANCE.runFreezeListeners(registry.key(), BuiltInRegistriesExt.STATIC_ACCESS_CONVERSIONS);
    }
}
