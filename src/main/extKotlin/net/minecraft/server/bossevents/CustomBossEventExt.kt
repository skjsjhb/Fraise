package net.minecraft.server.bossevents

import org.bukkit.boss.KeyedBossBar

interface CustomBossEventExt {
    fun getBukkitEntity(): KeyedBossBar
}
