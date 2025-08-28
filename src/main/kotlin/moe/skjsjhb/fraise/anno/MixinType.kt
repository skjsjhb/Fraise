package moe.skjsjhb.fraise.anno

/**
 * Annotations over mixin types to selectively disable some.
 */
class MixinType {
    /**
     * Required for the code to compile and run.
     */
    @Retention(AnnotationRetention.BINARY)
    @Target(AnnotationTarget.CLASS)
    annotation class Required

    /**
     * Fixes applied to the vanilla game.
     */
    @Retention(AnnotationRetention.BINARY)
    @Target(AnnotationTarget.CLASS)
    annotation class Fix

    /**
     * Optional optimizations.
     */
    @Retention(AnnotationRetention.BINARY)
    @Target(AnnotationTarget.CLASS)
    annotation class Optimization
}
