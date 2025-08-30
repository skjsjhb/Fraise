package net.minecraft.core

import net.minecraft.resources.ResourceKey
import java.util.*

interface `HolderLookup$RegistryLookupExt`<T> {
    // The cast is only valid for instances of the delegate class
    // This is desired as all other instances shall override this method
    fun getValueForCopying(key: ResourceKey<T>): Optional<T> =
        (this as HolderLookup.RegistryLookup.Delegate<*>)
            .parent()
            .let { it as `HolderLookup$RegistryLookupExt`<T> }
            .getValueForCopying(key)
}
