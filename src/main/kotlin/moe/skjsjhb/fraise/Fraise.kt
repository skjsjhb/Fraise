package moe.skjsjhb.fraise

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Fraise : ModInitializer {
    private val logger = LoggerFactory.getLogger("Fraise")

    override fun onInitialize() {
        logger.info("Welcome to Fraise!")
    }
}