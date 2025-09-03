package net.minecraft.network.protocol.login

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.login.custom.CustomQueryPayload
import net.minecraft.resources.ResourceLocation

@JvmRecord
data class `ClientboundCustomQueryPacket$PlayerInfoChannelPayload`(
    val id: ResourceLocation,
    val buffer: FriendlyByteBuf
) : CustomQueryPayload {
    override fun id(): ResourceLocation = id

    override fun write(out: FriendlyByteBuf) {
        out.writeBytes(buffer.copy())
    }
}
