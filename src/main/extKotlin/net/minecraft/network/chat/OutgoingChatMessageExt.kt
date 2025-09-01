package net.minecraft.network.chat

import net.minecraft.server.level.ServerPlayer

interface OutgoingChatMessageExt {
    fun sendToPlayer(sender: ServerPlayer, filterMaskEnabled: Boolean, params: ChatType.Bound, unsigned: Component?) {
        (this as OutgoingChatMessage).sendToPlayer(sender, filterMaskEnabled, params)
    }
}
