package moe.skjsjhb.fraise.mixin.net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket$QueryAnswerPayload;
import net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerboundCustomQueryAnswerPacket.class)
public class ServerboundCustomQueryAnswerPacketMixin {

    @Shadow
    @Final
    private static int MAX_PAYLOAD_SIZE;

    @Inject(method = "readPayload", at = @At("HEAD"), cancellable = true)
    private static void readPayload(
        int transactionId,
        FriendlyByteBuf buffer,
        CallbackInfoReturnable<CustomQueryAnswerPayload> cir
    ) {
        FriendlyByteBuf buf = buffer.readNullable((buf2) -> {
            int readableBytes = buf2.readableBytes();
            if (readableBytes >= 0 && readableBytes <= MAX_PAYLOAD_SIZE) {
                return new FriendlyByteBuf(buf2.readBytes(readableBytes));
            } else {
                throw new IllegalArgumentException("Payload may not be larger than " + MAX_PAYLOAD_SIZE + " bytes");
            }
        });

        if (buf != null) {
            cir.setReturnValue(new ServerboundCustomQueryAnswerPacket$QueryAnswerPayload(buf));
        } else {
            cir.setReturnValue(null);
        }
    }
}
