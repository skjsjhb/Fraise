package moe.skjsjhb.fraise

import org.apache.bcel.classfile.ClassParser
import org.apache.bcel.classfile.ConstantClass
import org.apache.bcel.classfile.ConstantUtf8
import org.apache.bcel.classfile.JavaClass
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.api.tasks.Optional
import java.io.Writer
import java.util.*
import java.util.jar.JarFile
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createDirectories

/** Associates data with sorted classes, grouped by the package name. */
typealias PackageClassMap<T> = TreeMap<String, TreeMap<String, T>>

/** Incubating flags annotated on implementations. */
typealias IncubatingFlags = Collection<String>

/** A pair containing the number of `(usable, finished)` implementations. */
typealias ImplCount = Pair<Int, Int>

abstract class ImplReportTask : DefaultTask() {
    @get:InputFile
    abstract val apiJar: RegularFileProperty

    @get:InputFile
    abstract val implJar: RegularFileProperty

    @get:OutputDirectory
    abstract val outDir: RegularFileProperty

    @get:Input
    abstract val annotationName: Property<String>

    private val annotationSignature by lazy { "L" + annotationName.get().replace(".", "/") + ";" }

    @get:Input
    @get:Optional
    abstract val ignore: Property<Iterable<String>>

    @TaskAction
    fun run() {
        // Collect interfaces
        val (interfaces, events) = scanApiJar()
        ignore.getOrNull()?.forEach { interfaces.remove(it) }

        val totalInterfaces = interfaces.size
        val totalEvents = events.size
        logger.lifecycle("Scanned API classes, $totalInterfaces interfaces and $totalEvents events found.")

        // Update interfaces and events status
        scanImplJar(interfaces, events)

        // Group by package
        val (packageImplStats, implStat) = packImpls(interfaces)
        val (implUsable, implDone) = implStat
        val (eventsFireStats, firedEventCount) = packEvents(events)

        val missingInterfaces = totalInterfaces - implUsable

        logger.lifecycle(
            "Scanned implementations, " +
            "$implDone/$totalInterfaces done, " +
            "$implUsable/$totalInterfaces usable, " +
            "$missingInterfaces/$totalInterfaces missing."
        )

        logger.lifecycle("Scanned events, $firedEventCount/$totalEvents fired.")

        genReport(
            packageImplStats,
            eventsFireStats,
            implUsable,
            implDone,
            totalInterfaces,
            firedEventCount,
            totalEvents
        )
    }

    private fun scanApiJar(): Pair<MutableMap<String, IncubatingFlags>, MutableMap<String, Boolean>> {
        val interfaces = mutableMapOf<String, IncubatingFlags>()
        val events = mutableMapOf<String, Boolean>()

        JarFile(apiJar.asFile.get()).useEachClass {
            if (it.isInterface && it.isPublic && !it.isAnnotation) {
                interfaces[it.className] = setOf("Err") // Default to not implemented
            } else if (!it.isAbstract) {
                it.interfaceNames.forEach { interfaces[it] = emptySet() } // Internal implementations
            }

            if (it.isClass && it.isPublic && !it.isAbstract && it.className.endsWith("Event")) {
                events[it.className] = false
            }
        }

        // Remove interfaces that are internally implemented
        interfaces.entries.removeIf { it.value.isEmpty() }

        return interfaces to events
    }

    private fun scanImplJar(interfaces: MutableMap<String, IncubatingFlags>, events: MutableMap<String, Boolean>) {
        JarFile(implJar.asFile.get()).useEachClass { clazz ->
            if (!clazz.isAbstract) {
                var incubatingFlags = getIncubatingFlags(clazz)

                // Process events by looking for references
                clazz.constantPool.forEach {
                    if (it is ConstantClass) {
                        val className = it.getBytes(clazz.constantPool).toString().replace('/', '.')

                        // Also scans for not implemented notations
                        if (className == "kotlin.NotImplementedError") {
                            incubatingFlags += "Err"
                        }

                        events.computeIfPresent(className) { _, _ -> true }
                    }
                }

                // Add the flag
                clazz.interfaceNames.forEach {
                    interfaces.computeIfPresent(it) { _, _ -> incubatingFlags }
                }
            }
        }
    }

    private fun getIncubatingFlags(clazz: JavaClass): IncubatingFlags {
        val annotation = clazz.annotationEntries.find {
            // Find the marker name
            clazz.constantPool.getConstant<ConstantUtf8>(it.typeIndex).bytes.toString() == annotationSignature
        } ?: return emptySet()

        return annotation.elementValuePairs.first().value.stringifyValue().split(",").toSet()
    }

    private fun packImpls(interfaces: Map<String, IncubatingFlags>): Pair<PackageClassMap<IncubatingFlags>, ImplCount> {
        val packageImplStats = PackageClassMap<IncubatingFlags>()
        var usableCount = 0
        var doneCount = 0

        interfaces.forEach { (className, status) ->
            val (pkgName, simpleName) = splitClassName(className)
            packageImplStats.getOrPut(pkgName) { TreeMap() }[simpleName] = status

            if (!status.contains("Err")) usableCount++
            if (status.isEmpty()) doneCount++
        }

        return packageImplStats to (usableCount to doneCount)
    }

    private fun packEvents(events: Map<String, Boolean>): Pair<PackageClassMap<Boolean>, Int> {
        val eventsFireStats = PackageClassMap<Boolean>()
        var firedCount = 0

        events.forEach { (name, fired) ->
            val (pkgName, simpleName) = splitClassName(name)
            eventsFireStats.getOrPut(pkgName) { TreeMap() }[simpleName] = fired
            if (fired) firedCount++
        }

        return eventsFireStats to firedCount
    }

    private fun splitClassName(fullName: String): Pair<String, String> {
        val (cn, pn) = fullName.reversed().split('.', limit = 2)
        return pn.reversed() to cn.reversed()
    }

    private fun genReport(
        impls: PackageClassMap<IncubatingFlags>,
        events: PackageClassMap<Boolean>,
        implUsable: Int,
        implDone: Int,
        totalInterfaces: Int,
        firedEvents: Int,
        totalEvents: Int
    ) {
        val out = outDir.asFile.get().toPath()
        out.createDirectories()

        val interfacesFile = out.resolve("interfaces.md")
        val eventsFile = out.resolve("events.md")

        interfacesFile.bufferedWriter().use {
            writeInterfaces(it, implUsable, implDone, totalInterfaces, impls)
        }

        eventsFile.bufferedWriter().use {
            writeEvents(it, totalEvents, firedEvents, events)
        }
    }

    private fun writeEvents(
        writer: Writer,
        totalEvents: Int,
        firedEvents: Int,
        eventsFireStats: PackageClassMap<Boolean>
    ) {
        writer.write(
            """
            # Events Firing Details
            
            Among $totalEvents events, $firedEvents can possibly be fired.
            
            ---
            
            - :tada: Should be fired.
            
            - :x: Won't be fired.
            
            ---
            
        """.trimIndent()
        )

        eventsFireStats.forEach { (pkg, events) ->
            writer.write("### `$pkg`\n")
            events.forEach { (eventName, isFired) ->
                val icon = if (isFired) ":tada:" else ":x:"
                writer.write("- `$eventName` $icon\n\n")
            }
        }
    }

    private fun writeInterfaces(
        writer: Writer,
        implUsable: Int,
        implDone: Int,
        totalInterfaces: Int,
        packageImplStats: PackageClassMap<IncubatingFlags>,
    ) {
        writer.write(
            """
            # Interfaces Implementation Details
            
            Among $totalInterfaces interfaces, $implDone are fully implemented, ${implUsable - implDone} work with limitations.
            
            ---
            
            - :tada: Fully implemented.
            
            - :potable_water: Only the Bukkit/Spigot part is implemented.
            
            - :cloud: Does not work on integrated servers.
            
            - :warning: Uses unsafe code.
            
            - :lock: May break reflections into implementation details.
            
            - :zzz: Contains dummy parts.
            
            - :x: Not implemented.
            
            ---
            
        """.trimIndent()
        )

        packageImplStats.forEach { (pkg, impls) ->
            writer.write("### `$pkg`\n")
            impls.forEach { (name, status) ->
                val icon = toReadableIncubatingEmojis(status)
                writer.write("- `$name` $icon\n\n")
            }
        }
    }

    private fun toReadableIncubatingEmojis(src: IncubatingFlags): String {
        if (src.isEmpty()) return ":tada:"

        val out = mutableListOf<String>()

        val emojiMap = mapOf(
            "Err" to ":x:",
            "Dummy" to ":zzz:",
            "Bukkit" to ":potable_water:",
            "Unsafe" to ":warning:",
            "Ref" to ":lock:",
            "Server" to ":cloud:"
        )

        emojiMap.forEach { (flag, emoji) ->
            if (src.contains(flag)) out.add(emoji)
        }

        return out.joinToString(" ")
    }

    private fun JarFile.useEachClass(action: (JavaClass) -> Unit) {
        use {
            entries().asSequence()
                .filter { it.name.endsWith(".class") }
                .forEach { ent ->
                    getInputStream(ent).use {
                        val clazz = ClassParser(it, ent.name).parse()
                        action(clazz)
                    }
                }
        }
    }
}
