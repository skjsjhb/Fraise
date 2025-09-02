package net.minecraft.network.protocol

import moe.skjsjhb.fraise.anno.CheckCast
import net.minecraft.network.Connection

@CheckCast
interface PacketExt {
    companion object {
        @JvmStatic
        fun invokeHasLargePacketFallback(self: Packet<*>) =
            if (self is PacketExt) self.hasLargePacketFallback() else defaultHasLargePacketFallback(self)

        @JvmStatic
        fun invokePacketTooLarge(self: Packet<*>, manager: Connection) =
            if (self is PacketExt) self.packetTooLarge(manager) else defaultPacketTooLarge(self, manager)

        private fun defaultHasLargePacketFallback(self: Packet<*>): Boolean = false
        private fun defaultPacketTooLarge(self: Packet<*>, manager: Connection): Boolean = false
    }

    fun hasLargePacketFallback(): Boolean = defaultHasLargePacketFallback(this as Packet<*>)
    fun packetTooLarge(manager: Connection): Boolean = defaultPacketTooLarge(this as Packet<*>, manager)
}
