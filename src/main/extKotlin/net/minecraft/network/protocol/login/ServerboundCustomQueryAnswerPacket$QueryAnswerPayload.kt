package net.minecraft.network.protocol.login

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.login.custom.CustomQueryAnswerPayload

class `ServerboundCustomQueryAnswerPacket$QueryAnswerPayload`(
    val buffer: FriendlyByteBuf
) : CustomQueryAnswerPayload {
    override fun write(out: FriendlyByteBuf) {
        out.writeBytes(buffer.copy())
    }
}
