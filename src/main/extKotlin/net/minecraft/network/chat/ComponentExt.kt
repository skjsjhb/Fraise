package net.minecraft.network.chat

import com.google.common.collect.Streams
import moe.skjsjhb.fraise.anno.CheckCast
import java.util.stream.Stream

@CheckCast
interface ComponentExt : Iterable<Component> {
    companion object {
        @JvmStatic
        fun invokeStream(self: Component): Stream<Component> =
            if (self is ComponentExt) self.stream() else defaultStream(self)

        @JvmStatic
        fun invokeIterator(self: Component): Iterator<Component> =
            if (self is ComponentExt) self.iterator() else defaultIterator(self)

        private fun defaultStream(self: Component): Stream<Component> =
            Streams.concat(Stream.of(self), self.siblings.stream().flatMap { invokeStream(it) })

        private fun defaultIterator(self: Component): Iterator<Component> = invokeStream(self).iterator()
    }

    fun stream(): Stream<Component> = defaultStream(this as Component)

    override fun iterator(): Iterator<Component> = defaultIterator(this as Component)
}
