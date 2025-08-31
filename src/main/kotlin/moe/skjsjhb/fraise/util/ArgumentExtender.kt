package moe.skjsjhb.fraise.util

import com.llamalad7.mixinextras.sugar.ref.LocalRef

class ArgumentExtender<T> {
    private val value: ThreadLocal<T> = ThreadLocal()
    private val hasValue: ThreadLocal<Boolean> = ThreadLocal.withInitial { false }

    fun set(v: T) {
        if (hasValue()) throw IllegalStateException("Value not consumed")

        hasValue.set(true)
        value.set(v)
    }

    fun hasValue(): Boolean = hasValue.get()

    fun get(): T {
        if (!hasValue()) throw IllegalStateException("No value present")

        val v = value.get()
        value.remove()
        hasValue.set(false)

        return v
    }

    fun clear() {
        hasValue.set(false)
        value.remove()
    }

    /**
     * Extracts the inner value to the given [LocalRef]. Throws an exception if no value is present.
     */
    fun dump(v: LocalRef<T>) {
        v.set(get())
    }

    /**
     * Like [dump], but does not throw upon missing value and returns a boolean indicating the status instead.
     */
    fun maybeDump(v: LocalRef<T>): Boolean =
        if (hasValue()) {
            v.set(get())
            true
        } else {
            false
        }


    /**
     * Sets the inner value to the given value, then executes the given [Runnable]. Clears the inner value upon
     * returning or throwing.
     */
    fun withValue(v: T, r: Runnable) {
        try {
            set(v)
            r.run()
        } finally {
            clear()
        }
    }
}
