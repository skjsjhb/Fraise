package moe.skjsjhb.fraise

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import org.slf4j.bridge.SLF4JBridgeHandler

object Fraise : ModInitializer {
    internal val logger = LoggerFactory.getLogger("Fraise")

    override fun onInitialize() {
        if (!SLF4JBridgeHandler.isInstalled()) {
            SLF4JBridgeHandler.install()
        }
        logger.info("Welcome to Fraise!")
    }
}
