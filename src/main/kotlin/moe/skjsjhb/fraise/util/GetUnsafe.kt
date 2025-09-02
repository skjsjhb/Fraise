package moe.skjsjhb.fraise.util

import sun.misc.Unsafe

object GetUnsafe {
    val unsafe: Unsafe by lazy {
        val fd = Unsafe::class.java.getDeclaredField("theUnsafe")
        fd.isAccessible = true
        fd.get(null) as Unsafe
    }
}
