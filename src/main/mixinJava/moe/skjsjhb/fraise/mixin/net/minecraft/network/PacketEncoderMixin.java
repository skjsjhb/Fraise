package moe.skjsjhb.fraise.mixin.net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.papermc.paper.adventure.PaperAdventure;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.PacketEncoder$PacketTooLargeException;
import net.minecraft.network.PacketEncoderExt;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PacketEncoder.class)
public class PacketEncoderMixin<T extends PacketListener> {
    @Inject(
        method = "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V",
        at = @At("HEAD")
    )
    private void setLocale(ChannelHandlerContext channelHandlerContext, Packet<T> packet, ByteBuf byteBuf, CallbackInfo ci) {
        PacketEncoderExt.ADVENTURE_LOCALE.set(channelHandlerContext.channel().attr(PaperAdventure.LOCALE_ATTRIBUTE).get());
    }

    // TODO: Is this really necessary? As an outbound we can just fire and forget...
    @Inject(
        method = "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V",
        at = @At(value = "RETURN")
    )
    private void throwOnLargePacket(ChannelHandlerContext channelHandlerContext, Packet<T> packet, ByteBuf byteBuf, CallbackInfo ci) {
        // This doesn't have to be placed in the finally block as the previous error will interrupt the encoding anyway
        int packetLength = byteBuf.readableBytes();
        if (packetLength > PacketEncoder$PacketTooLargeException.MAX_PACKET_SIZE ||
            // FIXME: Update this condition when `hasLargePacketFallback` is implemented
            (packetLength > PacketEncoder$PacketTooLargeException.MAX_FINAL_PACKET_SIZE && packet instanceof ClientboundContainerSetContentPacket)) {
            throw new PacketEncoder$PacketTooLargeException(packet, packetLength);
        }
    }
}
