package moe.skjsjhb.fraise.mixin.net.minecraft.commands;

import io.papermc.paper.brigadier.NullCommandSender;
import net.minecraft.commands.CommandSourceExt;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.commands.CommandSource$1")
public class CommandSource$1Mixin implements CommandSourceExt {

    @Override
    public CommandSender getBukkitSender(CommandSourceStack wrapper) {
        return NullCommandSender.INSTANCE;
    }
}
