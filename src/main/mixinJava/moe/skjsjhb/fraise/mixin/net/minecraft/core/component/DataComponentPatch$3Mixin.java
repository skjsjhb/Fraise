package moe.skjsjhb.fraise.mixin.net.minecraft.core.component;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.papermc.paper.util.sanitizer.ItemComponentSanitizer;
import io.papermc.paper.util.sanitizer.ItemObfuscationSession;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(targets = "net.minecraft.core.component.DataComponentPatch$3")
public class DataComponentPatch$3Mixin {
    @Definition(id = "i", local = @Local(type = int.class, ordinal = 0))
    @Expression("i = 0")
    @Inject(
        method = "encode(Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/minecraft/core/component/DataComponentPatch;)V",
        at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private void createObfSession(
        RegistryFriendlyByteBuf registryFriendlyByteBuf,
        DataComponentPatch dataComponentPatch,
        CallbackInfo ci,
        @Share("itemObfuscationSession") LocalRef<ItemObfuscationSession> itemObfuscationSession
    ) {
        if (dataComponentPatch.isEmpty()) {
            itemObfuscationSession.set(null);
        } else {
            itemObfuscationSession.set(ItemObfuscationSession.currentSession());
        }
    }

    @Inject(
        method = "encode(Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/minecraft/core/component/DataComponentPatch;)V",
        at = @At(
            value = "INVOKE",
            target = "Lit/unimi/dsi/fastutil/objects/Reference2ObjectMap$Entry;getValue()Ljava/lang/Object;",
            ordinal = 0
        )
    )
    private void maybeSkipIndex(
        RegistryFriendlyByteBuf registryFriendlyByteBuf,
        DataComponentPatch dataComponentPatch,
        CallbackInfo ci,
        @Share("itemObfuscationSession") LocalRef<ItemObfuscationSession> itemObfuscationSession,
        @Local Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> entry,
        @Local(ordinal = 0) LocalIntRef i
    ) {
        boolean shouldUndo = entry.getValue().isPresent() &&
            ItemComponentSanitizer.shouldDrop(itemObfuscationSession.get(), entry.getKey());
        if (shouldUndo) i.set(i.get() - 1);
    }


    @Inject(
        method = "encode(Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/minecraft/core/component/DataComponentPatch;)V",
        at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z", ordinal = 1)
    )
    private void alterOptional(
        RegistryFriendlyByteBuf registryFriendlyByteBuf,
        DataComponentPatch dataComponentPatch,
        CallbackInfo ci,
        @Share("itemObfuscationSession") LocalRef<ItemObfuscationSession> itemObfuscationSession,
        @Local Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> entryx,
        @Local LocalRef<Optional<?>> optional
    ) {
        optional.set(
            ItemComponentSanitizer.override(itemObfuscationSession.get(), entryx.getKey(), entryx.getValue())
        );
    }

    @WrapMethod(method = "encodeComponent")
    private void safeEncodeComponent(
        RegistryFriendlyByteBuf buffer,
        DataComponentType<?> component,
        Object value,
        Operation<Void> original
    ) {
        try {
            original.call(buffer, component, value);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding component " + component, e);
        }
    }
}
