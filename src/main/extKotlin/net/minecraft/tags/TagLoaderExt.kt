package net.minecraft.tags

import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent
import io.papermc.paper.tag.TagEventConfig
import moe.skjsjhb.fraise.util.ArgumentExtender
import net.minecraft.core.Registry
import net.minecraft.core.RegistryAccess
import net.minecraft.core.WritableRegistry
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager

interface TagLoaderExt<T> {
    companion object {
        @JvmField
        val `build$$eventConfig` = ArgumentExtender<TagEventConfig<*, *>?>()

        @JvmField
        val `loadTagsForExistingRegistries$$cause` = ArgumentExtender<ReloadableRegistrarEvent.Cause>()

        @JvmField
        val `syn61306$$cause` = ArgumentExtender<ReloadableRegistrarEvent.Cause>()

        @JvmField
        val `loadPendingTags$$cause` = ArgumentExtender<ReloadableRegistrarEvent.Cause>()

        @JvmField
        val `loadTagsForRegistry$$cause` = ArgumentExtender<ReloadableRegistrarEvent.Cause>()

        @JvmStatic
        fun loadTagsForExistingRegistries(
            rm: ResourceManager,
            ra: RegistryAccess,
            cause: ReloadableRegistrarEvent.Cause
        ): List<Registry.PendingTags<*>> =
            `loadTagsForExistingRegistries$$cause`.withValue(cause) {
                TagLoader.loadTagsForExistingRegistries(rm, ra)
            }

        @JvmStatic
        fun loadTagsForRegistry(
            rm: ResourceManager,
            reg: WritableRegistry<*>,
            cause: ReloadableRegistrarEvent.Cause
        ) {
            `loadTagsForRegistry$$cause`.withValue(cause) {
                TagLoader.loadTagsForRegistry(rm, reg)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun build(
        builders: Map<ResourceLocation, List<TagLoader.EntryWithSource>>,
        eventConfig: TagEventConfig<T, *>
    ): Map<ResourceLocation, List<T>> =
        `build$$eventConfig`.withValue(eventConfig) {
            (this as TagLoader<T>).build(builders)
        }
}
