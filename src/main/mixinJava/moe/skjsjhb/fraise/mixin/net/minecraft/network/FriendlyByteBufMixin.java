package moe.skjsjhb.fraise.mixin.net.minecraft.network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.FriendlyByteBufExt;
import net.minecraft.network.PacketEncoderExt;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;

@Mixin(FriendlyByteBuf.class)
public abstract class FriendlyByteBufMixin implements FriendlyByteBufExt {
    @Shadow
    @Final
    private static Gson GSON;

    @Shadow
    public abstract FriendlyByteBuf writeUtf(String string, int maxLength);

    @Unique
    public java.util.Locale adventure$locale;

    @Override
    public @NotNull Locale getAdventure$locale() {
        return adventure$locale;
    }

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void setLocale(ByteBuf source, CallbackInfo ci) {
        adventure$locale = PacketEncoderExt.ADVENTURE_LOCALE.get();
    }

    @Override
    public <T> void writeJsonWithCodec(@NotNull Codec<T> codec, T value, int maxLength) {
        DataResult<JsonElement> dataResult = codec.encodeStart(JsonOps.INSTANCE, value);
        writeUtf(GSON.toJson(dataResult.getOrThrow(exception -> new EncoderException("Failed to encode: " + exception + " " + value))), maxLength);
    }

    @WrapMethod(method = "writeNbt(Lio/netty/buffer/ByteBuf;Lnet/minecraft/nbt/Tag;)V")
    private static void catchAllExceptions(ByteBuf buffer, Tag nbt, Operation<Void> original) {
        try {
            original.call(buffer, nbt);
        } catch (Exception ex) {
            if (ex instanceof EncoderException ee) throw ee;
            throw new EncoderException(ex);
        }
    }
}
