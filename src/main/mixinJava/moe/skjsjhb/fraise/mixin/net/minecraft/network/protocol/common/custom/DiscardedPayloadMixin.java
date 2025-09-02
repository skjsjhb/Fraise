package moe.skjsjhb.fraise.mixin.net.minecraft.network.protocol.common.custom;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.network.protocol.common.custom.DiscardedPayloadExt;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiscardedPayload.class)
public class DiscardedPayloadMixin implements DiscardedPayloadExt {
    @Unique
    public byte[] data;

    @Override
    public byte[] data() {
        return data;
    }

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void extraInit(ResourceLocation id, CallbackInfo ci) {
        if (DiscardedPayloadExt.init$$data.hasValue()) {
            data = DiscardedPayloadExt.init$$data.get();
        } else {
            data = null;
        }
    }

    @Inject(method = "method_56493", at = @At("RETURN"))
    private static void alwaysWrite(DiscardedPayload value, FriendlyByteBuf output, CallbackInfo ci) {
        byte[] data = ((DiscardedPayloadExt) (Object) value).data();
        if (data != null) output.writeBytes(data);
    }

    @Inject(
        method = "method_56491",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/FriendlyByteBuf;skipBytes(I)Lnet/minecraft/network/FriendlyByteBuf;"
        )
    )
    private static void storeData(
        int i,
        ResourceLocation id,
        FriendlyByteBuf buf,
        CallbackInfoReturnable<DiscardedPayload> cir,
        @Share("data") LocalRef<byte[]> data
    ) {
        data.set(new byte[i]);
        buf.readBytes(data.get());
    }

    @Redirect(method = "method_56491", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;skipBytes(I)Lnet/minecraft/network/FriendlyByteBuf;"))
    private static FriendlyByteBuf noSkip(FriendlyByteBuf instance, int length) {
        return instance;
    }

    @Inject(method = "method_56491", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;<init>(Lnet/minecraft/resources/ResourceLocation;)V"))
    private static void attachData(
        int i,
        ResourceLocation resourceLocation,
        FriendlyByteBuf friendlyByteBuf,
        CallbackInfoReturnable<DiscardedPayload> cir,
        @Share("data") LocalRef<byte[]> data
    ) {
        // This class has no super so no catching needed
        DiscardedPayloadExt.init$$data.set(data.get());
    }
}
