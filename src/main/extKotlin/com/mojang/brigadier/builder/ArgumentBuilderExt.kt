package com.mojang.brigadier.builder

import java.util.function.Predicate

object ArgumentBuilderExt {
    @JvmStatic
    private val DEFAULT_REQUIREMENT: Predicate<Any?> = Predicate { true }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <S> defaultRequirement(): Predicate<S> = DEFAULT_REQUIREMENT as Predicate<S>
}
