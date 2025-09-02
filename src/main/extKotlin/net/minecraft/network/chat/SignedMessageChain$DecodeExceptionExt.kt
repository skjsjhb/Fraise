package net.minecraft.network.chat

import moe.skjsjhb.fraise.util.ArgumentExtender
import org.bukkit.event.player.PlayerKickEvent

interface `SignedMessageChain$DecodeExceptionExt` {
    companion object {
        @JvmField
        val `init$$cause` = ArgumentExtender<PlayerKickEvent.Cause>()

        @JvmStatic
        fun create(component: Component, cause: PlayerKickEvent.Cause) =
            `init$$cause`.withValue(cause) {
                SignedMessageChain.DecodeException(component)
            }
    }

    fun kickCause(): PlayerKickEvent.Cause
}
