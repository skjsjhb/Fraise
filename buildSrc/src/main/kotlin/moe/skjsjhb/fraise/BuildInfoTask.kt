package moe.skjsjhb.fraise

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.IOException
import kotlin.io.path.createDirectories

abstract class BuildInfoTask : DefaultTask() {
    init {
        outputs.upToDateWhen { false }
    }

    @get:OutputFile
    abstract val outFile: RegularFileProperty

    @TaskAction
    fun run() {
        val commit = exec("git describe --always --dirty")
        val branch = exec("git branch --no-color --show-current")
        val commitTime = exec("git show --no-patch --format=%ct")
        val lastTagCommit = exec("git rev-list --tags --no-walk --max-count=1").takeIf { it.isNotEmpty() }
        val commitRange = lastTagCommit?.plus("..").orEmpty() + "HEAD" // Build a commit range like `abc123..HEAD`
        val commitCount = exec("git rev-list --count $commitRange")

        val out = outFile.asFile.get()
        out.toPath().parent.createDirectories()
        out.writer().use {
            it.write("git.commit=$commit\n")
            it.write("git.branch=$branch\n")
            it.write("git.time=$commitTime\n")
            it.write("git.count=$commitCount\n")
        }
    }

    private fun exec(cmd: String): String {
        val rt = Runtime.getRuntime().exec(cmd.split(' ').toTypedArray()).onExit().get()
        if (rt.exitValue() != 0) {
            throw IOException("Command failed: $cmd")
        }
        rt.errorStream.close()
        rt.outputStream.close()
        return rt.inputReader().use { it.readText() }.trim()
    }
}
