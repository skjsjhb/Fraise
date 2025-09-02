package net.minecraft.network.protocol.game

import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.text.Component
import org.bukkit.craftbukkit.util.CraftChatMessage

object ClientboundSystemChatPacketExt {
    @Suppress("DEPRECATION")
    @JvmStatic
    fun create(content: Array<net.md_5.bungee.api.chat.BaseComponent>, overlay: Boolean) =
        ClientboundSystemChatPacket(CraftChatMessage.bungeeToVanilla(*content), overlay)

    @JvmStatic
    fun create(content: Component, overlay: Boolean) =
        ClientboundSystemChatPacket(PaperAdventure.asVanilla(content), overlay)
}
