package moe.skjsjhb.fraise.test

import org.bukkit.plugin.java.JavaPlugin

class TestPlugin : JavaPlugin() {
    override fun onLoad() {
        logger.info("Loading test plugin.")
    }

    override fun onEnable() {
        logger.info("Enabling test plugin.")
    }

    override fun onDisable() {
        logger.info("Disabling test plugin.")
    }
}

