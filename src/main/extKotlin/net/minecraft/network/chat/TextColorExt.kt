package net.minecraft.network.chat

import moe.skjsjhb.fraise.util.ArgumentExtender
import net.minecraft.ChatFormatting

interface TextColorExt {
    companion object {
        @JvmField
        val `init$$format` = ArgumentExtender<ChatFormatting>()

        @JvmStatic
        fun create(value: Int, name: String, format: ChatFormatting): TextColor =
            `init$$format`.withValue(format) {
                TextColor(value, name)
            }
    }

    fun format(): ChatFormatting?
}
