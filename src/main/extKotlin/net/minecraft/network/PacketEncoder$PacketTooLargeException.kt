package net.minecraft.network

import net.minecraft.network.protocol.Packet

class `PacketEncoder$PacketTooLargeException`(
    private val packet: Packet<*>,
    packetLength: Int
) : RuntimeException("PacketTooLarge - " + packet.javaClass.getSimpleName() + " is " + packetLength + ". Max is " + MAX_PACKET_SIZE) {
    companion object {
        const val MAX_FINAL_PACKET_SIZE = (1 shl 21) - 1
        const val MAX_PACKET_SIZE = 8388608
    }

    fun getPacket(): Packet<*> = packet
}
