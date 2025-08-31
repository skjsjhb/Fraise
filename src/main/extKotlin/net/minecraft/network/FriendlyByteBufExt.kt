package net.minecraft.network

import com.mojang.serialization.Codec
import java.util.*

interface FriendlyByteBufExt {
    val `adventure$locale`: Locale

    fun <T> writeJsonWithCodec(codec: Codec<T>, value: T, maxLength: Int)
}
