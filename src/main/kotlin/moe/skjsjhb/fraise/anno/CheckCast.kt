package moe.skjsjhb.fraise.anno

/**
 * Used on extension interfaces to state that an implementation of the original interface may not be a JVM subclass of
 * the extension. Extension interfaces annotated with it must use static dispatch as a fallback.
 *
 * This mainly happens for interfaces that contain no abstract method. Unlike regular interfaces which Paper is
 * guaranteed to rewrite their implementations, interfaces with only default methods "attach" them to the
 * implementations automatically without needing a patch, thus the mixin that adds the extension to implementations may
 * be missing.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class CheckCast()
