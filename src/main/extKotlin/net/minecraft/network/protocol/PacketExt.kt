package net.minecraft.network.protocol

import net.minecraft.network.Connection

interface PacketExt {
    fun hasLargePacketFallback(): Boolean = false
    fun packetTooLarge(manager: Connection): Boolean = false
}
