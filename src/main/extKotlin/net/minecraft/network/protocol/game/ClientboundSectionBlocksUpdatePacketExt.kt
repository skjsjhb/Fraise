package net.minecraft.network.protocol.game

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap
import it.unimi.dsi.fastutil.shorts.ShortSet
import moe.skjsjhb.fraise.util.GetUnsafe
import net.minecraft.core.SectionPos
import net.minecraft.world.level.block.state.BlockState

interface ClientboundSectionBlocksUpdatePacketExt {
    companion object {
        @JvmStatic
        fun create(pos: SectionPos, ss: ShortSet, st: Array<BlockState>) =
            GetUnsafe.unsafe.allocateInstance(ClientboundSectionBlocksUpdatePacket::class.java)
                .let { it as ClientboundSectionBlocksUpdatePacket }
                .apply {
                    sectionPos = pos
                    positions = ss.toShortArray()
                    states = st
                }

        @JvmStatic
        fun create(pos: SectionPos, bs: Short2ObjectMap<BlockState>) = create(pos, bs.keys, bs.values.toTypedArray())
    }
}
