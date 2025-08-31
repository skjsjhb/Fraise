package net.minecraft.util

interface TickThrottlerExt {
    fun isIncrementAndUnderThreshold(): Boolean

    fun isIncrementAndUnderThreshold(step: Int, th: Int): Boolean
}
