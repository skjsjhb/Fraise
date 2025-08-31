package net.minecraft.commands.arguments

import com.mojang.brigadier.context.CommandContext
import moe.skjsjhb.fraise.util.ArgumentExtender
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.PlayerChatMessage
import java.util.function.Consumer

interface MessageArgumentExt {
    companion object {
        @JvmField
        val `resolveChatMessage$$message` = ArgumentExtender<MessageArgument.Message>()

        @JvmStatic
        fun resolveChatMessage(
            message: MessageArgument.Message,
            context: CommandContext<CommandSourceStack>,
            key: String,
            callback: Consumer<PlayerChatMessage>
        ) {
            `resolveChatMessage$$message`.withValue(message) {
                MessageArgument.resolveChatMessage(context, key, callback)
            }
        }
    }
}
