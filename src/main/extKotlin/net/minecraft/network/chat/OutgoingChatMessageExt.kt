package net.minecraft.network.chat

import moe.skjsjhb.fraise.anno.CheckCast
import net.minecraft.server.level.ServerPlayer

@CheckCast
interface OutgoingChatMessageExt {
    companion object {
        @JvmStatic
        fun invokeSendToPlayer(
            self: OutgoingChatMessage,
            sender: ServerPlayer,
            filterMaskEnabled: Boolean,
            params: ChatType.Bound,
            unsigned: Component?
        ) {
            if (self is OutgoingChatMessageExt) {
                self.sendToPlayer(sender, filterMaskEnabled, params, unsigned)
            } else {
                defaultSendToPlayer(self, sender, filterMaskEnabled, params, unsigned)
            }
        }

        private fun defaultSendToPlayer(
            self: OutgoingChatMessage,
            sender: ServerPlayer,
            filterMaskEnabled: Boolean,
            params: ChatType.Bound,
            unsigned: Component?
        ) {
            self.sendToPlayer(sender, filterMaskEnabled, params)
        }
    }

    fun sendToPlayer(sender: ServerPlayer, filterMaskEnabled: Boolean, params: ChatType.Bound, unsigned: Component?) {
        defaultSendToPlayer(this as OutgoingChatMessage, sender, filterMaskEnabled, params, unsigned)
    }
}
