package net.minecraft

object ChatFormattingExt {
    @JvmStatic
    fun getByHexValue(color: Int): ChatFormatting? = ChatFormatting.entries.find { it.color == color }
}
