package net.minecraft.network.chat

import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.contents.NbtContents
import net.minecraft.network.chat.contents.SelectorContents
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.world.entity.Entity
import java.util.*

interface ComponentUtilsExt {
    companion object {
        @JvmStatic
        fun updateSeparatorForEntity(
            source: CommandSourceStack,
            text: Optional<Component>,
            sender: Entity,
            depth: Int
        ): Optional<MutableComponent> {
            if (text.isEmpty || !isValidSelector(text.get())) return Optional.empty()
            return Optional.of(ComponentUtils.updateForEntity(source, text.get(), sender, depth))
        }

        @JvmStatic
        fun isValidSelector(component: Component): Boolean {
            val contents = component.contents

            if (contents is NbtContents || contents is SelectorContents) return false

            if (contents is TranslatableContents) {
                if (contents.args.any { it is Component && !isValidSelector(it) }) {
                    return false
                }
            }

            return true
        }
    }
}
