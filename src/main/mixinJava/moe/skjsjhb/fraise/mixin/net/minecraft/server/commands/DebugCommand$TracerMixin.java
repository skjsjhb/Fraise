package moe.skjsjhb.fraise.mixin.net.minecraft.server.commands;

import net.minecraft.commands.CommandSourceExt;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.server.commands.DebugCommand$Tracer")
public class DebugCommand$TracerMixin implements CommandSourceExt {
    @Override
    public @NotNull CommandSender getBukkitSender(@NotNull CommandSourceStack wrapper) {
        throw new UnsupportedOperationException();
    }
}
