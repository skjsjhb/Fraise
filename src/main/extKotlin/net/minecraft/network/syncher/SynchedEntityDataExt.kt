package net.minecraft.network.syncher

interface SynchedEntityDataExt {
    fun <T> markDirty(da: EntityDataAccessor<T>)
    fun packAll(): List<SynchedEntityData.DataValue<*>>
}
