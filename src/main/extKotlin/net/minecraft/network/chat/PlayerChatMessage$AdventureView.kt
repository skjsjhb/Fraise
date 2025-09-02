package net.minecraft.network.chat

import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.chat.SignedMessage
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component
import java.time.Instant

@Suppress("NonExtendableApiUsage")
class `PlayerChatMessage$AdventureView`(private val self: PlayerChatMessage) : SignedMessage {
    companion object {
        // XXX: The original method is a member, change this if they override it in future versions
        @JvmStatic
        fun of(self: PlayerChatMessage) = `PlayerChatMessage$AdventureView`(self)
    }

    override fun timestamp(): Instant = self.timeStamp()

    override fun salt(): Long = self.salt()

    override fun signature(): SignedMessage.Signature? = self.signature

    override fun unsignedContent(): Component? = self.unsignedContent?.let { PaperAdventure.asAdventure(it) }

    override fun message(): String = self.signedContent()

    override fun identity(): Identity = Identity.identity(self.sender())

    fun playerChatMessage() = self
}
