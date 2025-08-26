package moe.skjsjhb.fraise.conf

import moe.skjsjhb.fraise.Fraise
import net.fabricmc.loader.api.FabricLoader
import org.bukkit.configuration.file.YamlConfiguration
import java.io.IOException
import kotlin.io.path.*

/**
 * Global configuration entries.
 */
object FraiseConf {
    private val logger = Fraise.logger
    private val confPath = FabricLoader.getInstance().configDir.resolve("fraise.yaml")
    private val defaultConf by lazy { readerOfDefault().use { YamlConfiguration.loadConfiguration(it) } }
    private val conf by lazy { load() }

    /**
     * Whether to use the Paper brand info instead of the Fraise one.
     */
    val usePaperBrand: Boolean = withDefault { getBoolean("paper-brand", false) }

    /**
     * The preferred API patching method.
     */
    val patchMethod: PatchMethod =
        withDefault { getString("patch", "class")!!.uppercase().let { PatchMethod.valueOf(it) } }

    /**
     * API patching method.
     */
    enum class PatchMethod {
        /**
         * Patches using unsafe.
         */
        RUNTIME,

        /**
         * Patches using bytecode manipulation.
         */
        CLASS,

        /**
         * No patches, replaces with extendable alternatives.
         */
        EXT
    }

    // Runs the accessor for the active config and use the default as a fallback.
    private fun <T> withDefault(accessor: YamlConfiguration.() -> T): T = conf.accessor() ?: defaultConf.accessor()

    private fun load() =
        runCatching {
            saveDefault()
            confPath.reader()
                .use { YamlConfiguration.loadConfiguration(it) }
                .also {
                    // Fraise requires manual config updating, so error on mismatch
                    assertVersion(it, defaultConf)
                }
        }.onFailure {
            logger.error("Failed to load config from ${confPath.pathString}", it)
            logger.error("Not proceeding to prevent destructive operations.")
            logger.error("Please fix the config, or remove it to let Fraise regenerate.")
        }.getOrThrow()

    private fun assertVersion(src: YamlConfiguration, exp: YamlConfiguration) {
        val cv = src.getInt("version")
        val ev = exp.getInt("version")

        if (cv != ev) {
            throw AssertionError("Version mismatch, expecting $ev, got $cv")
        }
    }

    private fun readerOfDefault() =
        javaClass.getResourceAsStream("/fraise.yaml")?.reader() ?: throw IOException("Missing default config")

    private fun saveDefault() {
        if (confPath.exists()) return

        logger.debug("Saving default config to ${confPath.pathString}")
        confPath.createParentDirectories()

        readerOfDefault().use { r ->
            confPath.writer().use { w ->
                r.transferTo(w)
            }
        }
    }
}
