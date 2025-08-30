package net.minecraft.core.component

interface `DataComponentPatch$BuilderExt` {
    fun copy(orig: DataComponentPatch)

    fun clear(type: DataComponentType<*>)

    fun isSet(type: DataComponentType<*>): Boolean

    fun isEmpty(): Boolean
}
