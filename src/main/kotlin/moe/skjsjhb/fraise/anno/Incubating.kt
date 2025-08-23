package moe.skjsjhb.fraise.anno

/**
 * States that the implementation does not fully match its API specifications. The value describes problems that need to
 * be addressed by picking one or some (joined with comma) of the flags below:
 *
 * - `"Dummy"`: Has dummy parts.
 * - `"Bukkit"`: Covers only the Bukkit API, missing the Paper part.
 * - `"Unsafe"`: Uses unsafe code.
 * - `"Ref"`: May break reflections.
 * - `"Server"`: Only works on dedicated servers.
 *
 * Our scanner automatically tests whether the implementation is incomplete (contains [NotImplementedError]).
 * Implementations should annotate the rest flags correctly for it to know additional properties about the status.
 */
annotation class Incubating(val value: String = "Err")
