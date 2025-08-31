package net.minecraft.resources

import net.minecraft.core.HolderLookup

interface `RegistryOps$RegistryInfoLookupExt` {
    // XXX: This API is incomplete in Paper, check its implementations when updating
    fun lookupForValueCopyViaBuilders(): HolderLookup.Provider
}
