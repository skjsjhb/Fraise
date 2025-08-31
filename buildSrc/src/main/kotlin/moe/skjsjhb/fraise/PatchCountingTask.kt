package moe.skjsjhb.fraise

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class PatchCountingTask : DefaultTask() {
    @get:InputDirectory
    abstract val activeDir: RegularFileProperty

    @get:InputDirectory
    abstract val completedDir: RegularFileProperty

    @get:InputDirectory
    abstract val partialDir: RegularFileProperty

    @get:InputDirectory
    abstract val ignoredDir: RegularFileProperty

    @get:OutputFile
    abstract val outFile: RegularFileProperty

    @get:OutputFile
    abstract val jsonOutFile: RegularFileProperty

    @TaskAction
    fun run() {
        val active = fileCount(activeDir.asFile.get())
        val completed = fileCount(completedDir.asFile.get())
        val ignored = fileCount(ignoredDir.asFile.get())
        val partial = fileCount(partialDir.asFile.get())

        val total = active + completed + ignored
        val pct = (completed + ignored + partial) / total.toDouble()

        val status = when {
            pct > 0.9 -> "Mostly..."
            pct > 0.7 -> "Closer and closer..."
            pct > 0.5 -> "There's less to do than done..."
            pct > 0.3 -> "Still far away..."
            pct > 0.1 -> "Just started..."
            else -> "Not even close..."
        }

        val progress = "%.2f".format(pct * 100) + "%"
        val msg =
            "Total $total, ported $completed (partial $partial), ignored $ignored, $progress done."

        outFile.asFile.get().writeText(
            """
            # Are we patched yet?
            
            **$status**
            
            $msg
        """.trimIndent()
        )

        jsonOutFile.asFile.get().writeText(
            """
            {
                "progress": "$progress"
            }
        """.trimIndent()
        )

        logger.lifecycle(msg)
    }

    private fun fileCount(dir: File): Int = dir.walkTopDown().filter { it.isFile }.count()
}
