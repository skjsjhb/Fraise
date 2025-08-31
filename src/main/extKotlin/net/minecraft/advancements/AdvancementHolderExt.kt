package net.minecraft.advancements

import org.bukkit.advancement.Advancement

interface AdvancementHolderExt {
    fun toBukkit(): Advancement
}
