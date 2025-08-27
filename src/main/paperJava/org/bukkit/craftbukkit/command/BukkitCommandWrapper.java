package org.bukkit.craftbukkit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import kotlin.NotImplementedError;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.CraftServer;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@Deprecated(forRemoval = true) // Paper - Don't use
public class BukkitCommandWrapper implements com.mojang.brigadier.Command<CommandSourceStack>, Predicate<CommandSourceStack>, SuggestionProvider<CommandSourceStack>
    // , com.destroystokyo.paper.brigadier.BukkitBrigadierCommand<CommandSourceStack>  // throw new NotImplementedError();
{ // Paper

    private final CraftServer server;
    private final Command command;

    public BukkitCommandWrapper(CraftServer server, Command command) {
        this.server = server;
        this.command = command;
    }

    public LiteralCommandNode<CommandSourceStack> register(CommandDispatcher<CommandSourceStack> dispatcher, String label) {
        throw new NotImplementedError();
        // com.mojang.brigadier.tree.RootCommandNode<CommandSourceStack> root = dispatcher.getRoot();
        // LiteralCommandNode<CommandSourceStack> literal = LiteralArgumentBuilder.<CommandSourceStack>literal(label).requires(this).executes(this).build();
        // LiteralCommandNode<CommandSourceStack> defaultNode = literal;
        // com.mojang.brigadier.tree.ArgumentCommandNode<CommandSourceStack, String> defaultArgs = RequiredArgumentBuilder.<CommandSourceStack, String>argument("args", StringArgumentType.greedyString()).suggests(this).executes(this).build();
        // literal.addChild(defaultArgs);
        // com.destroystokyo.paper.event.brigadier.CommandRegisteredEvent<CommandSourceStack> event = new com.destroystokyo.paper.event.brigadier.CommandRegisteredEvent<>(label, this, this.command, root, literal, defaultArgs);
        // if (!event.callEvent()) {
        //     return null;
        // }
        // literal = event.getLiteral();
        // if (event.isRawCommand()) {
        //     defaultNode.clientNode = literal;
        //     literal = defaultNode;
        // }
        // root.addChild(literal);
        // return literal;
    }

    @Override
    public boolean test(CommandSourceStack wrapper) {
        throw new NotImplementedError();
        // return this.command.testPermissionSilent(wrapper.getBukkitSender());
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        throw new NotImplementedError();
        // CommandSender sender = context.getSource().getBukkitSender();
        //
        // try {
        //     return this.server.dispatchCommand(sender, context.getRange().get(context.getInput())) ? 1 : 0; // Paper - Fix commands from signs not firing command events; actually use the StringRange from context
        // } catch (CommandException ex) {
        //     sender.sendMessage(org.bukkit.ChatColor.RED + "An internal error occurred while attempting to perform this command");
        //     this.server.getLogger().log(Level.SEVERE, null, ex);
        //     return 0;
        // }
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        throw new NotImplementedError();
        // List<String> results = this.server.tabComplete(context.getSource().getBukkitSender(), builder.getInput(), context.getSource().getLevel(), context.getSource().getPosition(), true);
        //
        // // Defaults to sub nodes, but we have just one giant args node, so offset accordingly
        // builder = builder.createOffset(builder.getInput().lastIndexOf(' ') + 1);
        //
        // for (String s : results) {
        //     builder.suggest(s);
        // }
        //
        // return builder.buildFuture();
    }
}
