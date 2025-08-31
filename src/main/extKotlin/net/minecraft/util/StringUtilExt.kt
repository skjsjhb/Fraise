package net.minecraft.util

object StringUtilExt {
    @JvmStatic
    fun isReasonablePlayerName(name: String): Boolean {
        if (name.length !in 1..16) return false

        return name.all {
            it in 'a'..'z' || it in 'A'..'Z' || it in '0'..'9' || it == '_' || it == '.'
        }
    }
}
