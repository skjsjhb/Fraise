package net.minecraft.network.protocol.common.custom

import moe.skjsjhb.fraise.util.ArgumentExtender
import net.minecraft.resources.ResourceLocation

interface DiscardedPayloadExt {
    companion object {
        @JvmField
        val `init$$data` = ArgumentExtender<ByteArray?>()

        @JvmStatic
        fun create(id: ResourceLocation, data: ByteArray?) =
            `init$$data`.withValue(data) {
                DiscardedPayload(id)
            }
    }

    fun data(): ByteArray?
}
