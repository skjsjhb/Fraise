package net.minecraft.network

import java.util.*

object PacketEncoderExt {
    @JvmField
    val ADVENTURE_LOCALE: ThreadLocal<Locale?> = ThreadLocal.withInitial { null }
}
