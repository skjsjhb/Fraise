package net.minecraft.commands

import org.bukkit.command.CommandSender

interface CommandSourceExt {
    fun getBukkitSender(wrapper: CommandSourceStack): CommandSender
}
