package net.minecraft.core.registries

import io.papermc.paper.registry.data.util.Conversions
import net.minecraft.resources.RegistryOps
import net.minecraft.server.RegistryLayer

interface BuiltInRegistriesExt {
    companion object {
        @JvmField
        val STATIC_ACCESS_CONVERSIONS: Conversions =
            Conversions(RegistryOps.HolderLookupAdapter(RegistryLayer.STATIC_ACCESS))

        @JvmStatic
        fun bootStrapWithCallback(cb: Runnable) {
            // XXX: Must we freeze it here? Consider other mods...
            BuiltInRegistries.REGISTRY.freeze()
            BuiltInRegistries.createContents()
            cb.run()
            BuiltInRegistries.freeze()
            BuiltInRegistries.validate(BuiltInRegistries.REGISTRY)
        }
    }
}
