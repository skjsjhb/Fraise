package net.minecraft.core

interface MappedRegistryExt<T> {
    fun clearIntrusiveHolder(instance: T)
}
