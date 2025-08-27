package io.papermc.paper.adventure;

import net.minecraft.network.chat.ChatDecorator;
import net.minecraft.server.MinecraftServer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public abstract class ImprovedChatDecorator implements ChatDecorator { // throw new NotImplementedError(); Make this final
    private final MinecraftServer server;

    public ImprovedChatDecorator(final MinecraftServer server) {
        this.server = server;
    }

    // throw new NotImplementedError();
    // @Override
    // public CompletableFuture<Component> decorate(final @Nullable ServerPlayer sender, final Component message) {
    //     return decorate(this.server, sender, null, message);
    // }

    // throw new NotImplementedError();
    // @Override
    // public CompletableFuture<Component> decorate(final @Nullable ServerPlayer sender, final @Nullable CommandSourceStack commandSourceStack, final Component message) {
    //     return decorate(this.server, sender, commandSourceStack, message);
    // }

    // throw new NotImplementedError();
    // private static CompletableFuture<Component> decorate(final MinecraftServer server, final @Nullable ServerPlayer player, final @Nullable CommandSourceStack commandSourceStack, final Component originalMessage) {
    //     return CompletableFuture.supplyAsync(() -> {
    //         final net.kyori.adventure.text.Component initialResult = PaperAdventure.asAdventure(originalMessage);
    //
    //         final @Nullable CraftPlayer craftPlayer = player == null ? null : player.getBukkitEntity();
    //
    //         final AsyncChatDecorateEvent event;
    //         if (commandSourceStack != null) {
    //             // TODO more command decorate context
    //             event = new AsyncChatCommandDecorateEvent(craftPlayer, initialResult);
    //         } else {
    //             event = new AsyncChatDecorateEvent(craftPlayer, initialResult);
    //         }
    //
    //         if (event.callEvent()) {
    //             return PaperAdventure.asVanilla(event.result());
    //         }
    //
    //         return originalMessage;
    //     }, server.chatExecutor);
    // }
}
