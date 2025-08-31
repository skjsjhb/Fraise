package moe.skjsjhb.fraise.mixin.net.minecraft.commands.arguments;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.MessageArgumentExt;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(MessageArgument.class)
public class MessageArgumentMixin {
    @Inject(method = "resolveChatMessage", at = @At("HEAD"))
    private static void useArgument(
        CommandContext<CommandSourceStack> context,
        String key,
        Consumer<PlayerChatMessage> callback,
        CallbackInfo ci,
        @Share("message") LocalRef<MessageArgument.Message> message,
        @Share("hasMessage") LocalBooleanRef hasMessage
    ) {
        hasMessage.set(MessageArgumentExt.resolveChatMessage$$message.maybeDump(message));
    }

    @ModifyVariable(method = "resolveChatMessage", at = @At("STORE"))
    private static MessageArgument.Message extendResolveChatMessage(
        MessageArgument.Message original,
        @Share("message") LocalRef<MessageArgument.Message> message,
        @Share("hasMessage") LocalBooleanRef hasMessage
    ) {
        if (hasMessage.get()) {
            return message.get();
        }

        return original;
    }
}
