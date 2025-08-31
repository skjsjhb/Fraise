package net.minecraft.network

import java.util.*

interface PacketEncoderExt {
    companion object {
        @JvmField
        val ADVENTURE_LOCALE: ThreadLocal<Locale?> = ThreadLocal.withInitial { null }
    }
}
