package moe.skjsjhb.fraise.misc

import java.time.Instant
import java.util.*

object BuildInfo {
    private val props by lazy {
        Properties().apply {
            // Missing build info is not fatal
            javaClass.getResourceAsStream("/build.properties")?.use { load(it) }
        }
    }

    val commit: String? by lazy { props.getProperty("git.commit") }
    val branch: String? by lazy { props.getProperty("git.branch") }

    private val fallbackTime by lazy { Instant.now() }
    val time: Instant by lazy {
        props.getProperty("git.time")?.let { Instant.ofEpochSecond(it.toLong()) } ?: fallbackTime
    }

    val buildNumber: Int? by lazy { props.getProperty("git.count")?.toInt() }
}
