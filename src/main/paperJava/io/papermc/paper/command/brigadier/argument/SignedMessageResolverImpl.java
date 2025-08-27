package io.papermc.paper.command.brigadier.argument;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import kotlin.NotImplementedError;
import net.kyori.adventure.chat.SignedMessage;
import net.minecraft.commands.arguments.MessageArgument;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

@NullMarked
public record SignedMessageResolverImpl(MessageArgument.Message message) implements SignedMessageResolver {

    @Override
    public String content() {
        return this.message.text();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public CompletableFuture<SignedMessage> resolveSignedMessage(final String argumentName, final CommandContext erased) throws CommandSyntaxException {
        throw new NotImplementedError();
        // final CompletableFuture<SignedMessage> future = new CompletableFuture<>();
        //
        // final MessageArgument.Message response = ((CommandContext<CommandSourceStack>) erased).getArgument(argumentName, SignedMessageResolverImpl.class).message;
        // MessageArgument.resolveChatMessage(response, erased, argumentName, (message) -> {
        //     future.complete(message.adventureView());
        // });
        // return future;
    }
}
