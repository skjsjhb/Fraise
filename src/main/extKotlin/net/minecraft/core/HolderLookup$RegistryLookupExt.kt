package net.minecraft.core

import moe.skjsjhb.fraise.anno.CheckCast
import net.minecraft.resources.ResourceKey
import java.util.*

@CheckCast
interface `HolderLookup$RegistryLookupExt`<T> {
    companion object {
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> invokeGetValueForCopying(self: HolderLookup.RegistryLookup<T>, key: ResourceKey<T>): Optional<T> =
            if (self is `HolderLookup$RegistryLookupExt`<*>) {
                (self as `HolderLookup$RegistryLookupExt`<T>).getValueForCopying(key)
            } else {
                defaultGetValueForCopying(self, key)
            }

        @Suppress("UNCHECKED_CAST")
        private fun <T> defaultGetValueForCopying(
            self: HolderLookup.RegistryLookup<T>,
            key: ResourceKey<T>
        ): Optional<T> {
            if (self !is HolderLookup.RegistryLookup.Delegate<*>) {
                throw UnsupportedOperationException("This method must be overridden")
            }

            return invokeGetValueForCopying(self.parent() as HolderLookup.RegistryLookup<T>, key)
        }
    }

    // The cast is only valid for instances of the delegate class
    // This is desired as all other instances shall override this method
    @Suppress("UNCHECKED_CAST")
    fun getValueForCopying(key: ResourceKey<T>): Optional<T> =
        defaultGetValueForCopying(this as HolderLookup.RegistryLookup<T>, key)
}
