package net.minecraft.network

import io.papermc.paper.connection.DisconnectionReason
import moe.skjsjhb.fraise.util.ArgumentExtender
import net.minecraft.network.chat.Component
import java.net.URI
import java.nio.file.Path
import java.util.*

interface DisconnectionDetailsExt {
    companion object {
        @JvmField
        val `init$$quitMessage` = ArgumentExtender<Optional<Component>>()

        @JvmField
        val `init$$disconnectionReason` = ArgumentExtender<Optional<DisconnectionReason>>()

        fun create(
            reason: Component,
            report: Optional<Path>,
            bugReportLink: Optional<URI>,
            quitMessage: Optional<Component>,
            disconnectionReason: Optional<DisconnectionReason>
        ): DisconnectionDetails =
            `init$$quitMessage`.withValue(quitMessage) {
                `init$$disconnectionReason`.withValue(disconnectionReason) {
                    DisconnectionDetails(reason, report, bugReportLink)
                }
            }
    }

    fun quitMessage(): Optional<Component>
    fun disconnectionReason(): Optional<DisconnectionReason>
}
