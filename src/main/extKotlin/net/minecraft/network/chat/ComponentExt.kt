package net.minecraft.network.chat

import com.google.common.collect.Streams
import java.util.stream.Stream

interface ComponentExt : Iterable<Component> {
    fun stream(): Stream<Component> = (this as Component).run {
        Streams.concat(Stream.of(this), siblings.stream().flatMap { (it as ComponentExt).stream() })
    }

    override fun iterator(): Iterator<Component> = stream().iterator()
}
