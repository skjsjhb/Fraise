package net.minecraft

object ChatFormattingExt {
    fun getByHexValue(color: Int): ChatFormatting? = ChatFormatting.entries.find { it.color == color }
}
