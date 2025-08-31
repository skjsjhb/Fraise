package net.minecraft.core.registries

import io.papermc.paper.registry.data.util.Conversions
import moe.skjsjhb.fraise.util.ArgumentExtender
import net.minecraft.resources.RegistryOps
import net.minecraft.server.RegistryLayer

interface BuiltInRegistriesExt {
    companion object {
        @JvmField
        val STATIC_ACCESS_CONVERSIONS: Conversions =
            Conversions(RegistryOps.HolderLookupAdapter(RegistryLayer.STATIC_ACCESS))

        @JvmField
        val `bootStrap$$cb` = ArgumentExtender<Runnable>()

        @JvmStatic
        fun bootStrap(cb: Runnable) {
            `bootStrap$$cb`.withValue(cb) {
                BuiltInRegistries.bootStrap()
            }
        }
    }
}
