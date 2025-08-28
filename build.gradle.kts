@file:Suppress("HasPlatformType", "unused", "UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import moe.skjsjhb.fraise.BuildInfoTask
import moe.skjsjhb.fraise.ImplReportTask
import moe.skjsjhb.fraise.PatchCountingTask
import net.fabricmc.loom.task.RunGameTask

plugins {
    kotlin("jvm") version "2.2.10"
    id("fabric-loom") version "1.11-SNAPSHOT"
    id("com.gradleup.shadow") version "9.0.2"
    idea
}

group = "moe.skjsjhb.fraise"
version = "0.1.0"

idea {
    module {
        isDownloadSources = true
    }
}

base {
    archivesName = "fraise"
}

loom {
    accessWidenerPath = file("src/main/resources/fraise.aw")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

sourceSets {
    // A compile-only source set for unported Paper sources, brings IDE support to make life easier
    val porting by creating {
        compileClasspath = sourceSets.main.get().compileClasspath
        java.srcDir("src/porting/java")
        resources.srcDir("src/porting/resources")
    }

    // Separate Paper sources for standalone formatting
    main {
        java.srcDir("src/main/paperJava")
        resources.srcDir("src/main/paperResources")
    }
}

loom {
    splitEnvironmentSourceSets()

    mods {
        maybeCreate("fraise").apply {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }
}

val bundleImplementation by configurations.creating
val bundleRuntimeOnly by configurations.creating

configurations {
    implementation {
        extendsFrom(bundleImplementation)
    }

    runtimeOnly {
        extendsFrom(bundleRuntimeOnly)
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.8")

    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.21.8:2025.07.20@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:0.17.2")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.132.0+1.21.8")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.5+kotlin.2.2.10")
    modImplementation(include("net.kyori:adventure-platform-fabric:6.6.0")!!)

    bundleImplementation("org.javassist:javassist:3.30.2-GA")
    bundleImplementation("org.slf4j:jul-to-slf4j:2.0.17")

    // https://github.com/PaperMC/Paper/blob/main/paper-server/build.gradle.kts
    bundleImplementation(project(":paper-api"))

    bundleImplementation("ca.spottedleaf:concurrentutil:0.0.3")
    bundleImplementation("org.jline:jline-terminal-ffm:3.27.1")
    bundleImplementation("org.jline:jline-terminal-jni:3.27.1")
    bundleImplementation("net.minecrell:terminalconsoleappender:1.3.0")
    bundleImplementation("net.kyori:adventure-text-serializer-ansi")

    bundleImplementation("org.apache.logging.log4j:log4j-core:2.24.1")

    bundleImplementation("com.velocitypowered:velocity-native:3.4.0-SNAPSHOT") {
        isTransitive = false
    }
    bundleImplementation("io.netty:netty-codec-haproxy:4.1.118.Final")
    bundleImplementation("org.apache.logging.log4j:log4j-iostreams:2.24.1")
    bundleImplementation("org.ow2.asm:asm-commons:9.8")
    bundleImplementation("org.spongepowered:configurate-yaml:4.2.0")

    // Paper keeps these for backward compatibility, yet they are sooooooo bloated :(
    bundleRuntimeOnly("commons-lang:commons-lang:2.6")
    bundleRuntimeOnly("org.xerial:sqlite-jdbc:3.49.1.0")
    bundleRuntimeOnly("com.mysql:mysql-connector-j:9.2.0")
    bundleRuntimeOnly("com.lmax:disruptor:3.4.4")

    bundleImplementation("com.googlecode.json-simple:json-simple:1.1.1") {
        isTransitive = false
    }

    bundleImplementation("net.neoforged:srgutils:1.0.9")
    bundleImplementation("net.neoforged:AutoRenamingTool:2.0.3")

    bundleImplementation("io.papermc:reflection-rewriter:0.0.3")
    bundleImplementation("io.papermc:reflection-rewriter-runtime:0.0.3")
    bundleImplementation("io.papermc:reflection-rewriter-proxy-generator:0.0.3")

    bundleImplementation("me.lucko:spark-api:0.1-20240720.200737-2")
    bundleImplementation("me.lucko:spark-paper:1.10.133-20250413.112336-1")
}

kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    // There are too many removals in Paper...
    options.isWarnings = false
}

listOf("compilePortingJava", "compilePortingKotlin", "processPortingResources", "portingClasses").forEach {
    tasks.getByName(it).enabled = false
}

val deployTestPlugin by tasks.registering(Copy::class) {
    description = "Deploy the test plugin."

    from(project(":test-plugin").tasks.jar.get().archiveFile)
    into(file("run/plugins"))
}

tasks.withType<RunGameTask>().configureEach {
    dependsOn(deployTestPlugin)
}

val genImplReport by tasks.registering(ImplReportTask::class) {
    group = "documentation"
    description = "Generates a report with detailed implementation progress information."

    annotationName = "moe.skjsjhb.fraise.anno.Incubating"
    apiJar = project(":paper-api").tasks.jar.get().archiveFile
    implJar = tasks.jar.get().archiveFile
    outDir = layout.buildDirectory.file("impl-report")
    ignore = setOf()
}

val emitBuildInfo by tasks.registering(BuildInfoTask::class) {
    group = "build"
    description = "Emits build information."

    outFile = layout.buildDirectory.file("build-info/build.properties")
}

val countPatches by tasks.registering(PatchCountingTask::class) {
    group = "documentation"
    description = "Generates a report with patch porting progress."

    activeDir = file("src/porting/patches")
    completedDir = file("src/porting/patched")
    ignoredDir = file("src/porting/wont-patch")
    outFile = layout.buildDirectory.file("patch-report/report.md")
}

tasks.processResources {
    from(emitBuildInfo.get().outFile)
}

// A shadow jar that has deprecated legacy dependencies removed (reduces size by 60%!)
val slimShadowJar by tasks.registering(ShadowJar::class) {
    archiveClassifier = "slim"

    // Current this project doesn't have runtime-only dependencies in use, only the legacy ones, so omitting the runtime
    // configuration gives us a slim (yet still self-contained) output.
    configurations = listOf(bundleImplementation)

    // Non-mod dependencies don't use deobfuscated symbols, omit them from remapping to make the build faster
    from(zipTree(tasks.remapJar.get().archiveFile))
}

// A complete jar for maximum compatibility
tasks.shadowJar {
    configurations = listOf(bundleRuntimeOnly)
    from(zipTree(slimShadowJar.get().archiveFile))
}

tasks.withType<ShadowJar>().configureEach {
    mergeServiceFiles()

    // A set of common dependencies provided by the game, exported from version profile and server jar
    val mojangProvided = listOf(
        "com.fasterxml.jackson.core:jackson-annotations",
        "com.fasterxml.jackson.core:jackson-core",
        "com.fasterxml.jackson.core:jackson-databind",
        "com.github.oshi:oshi-core",
        "com.github.stephenc.jcip:jcip-annotations",
        "com.google.code.gson:gson",
        "com.google.guava:failureaccess",
        "com.google.guava:guava",
        "com.microsoft.azure:msal4j",
        "com.mojang:authlib",
        "com.mojang:brigadier",
        "com.mojang:datafixerupper",
        "com.mojang:jtracy",
        "com.mojang:logging",
        "com.nimbusds:content-type",
        "com.nimbusds:lang-tag",
        "com.nimbusds:nimbus-jose-jwt",
        "com.nimbusds:oauth2-oidc-sdk",
        "commons-io:commons-io",
        "io.netty:netty-buffer",
        "io.netty:netty-codec",
        "io.netty:netty-common",
        "io.netty:netty-handler",
        "io.netty:netty-resolver",
        "io.netty:netty-transport",
        "io.netty:netty-transport-classes-epoll",
        "io.netty:netty-transport-native-unix-common",
        "it.unimi.dsi:fastutil",
        "net.java.dev.jna:jna",
        "net.java.dev.jna:jna-platform",
        "net.minidev:accessors-smart",
        "net.minidev:json-smart",
        "net.sf.jopt-simple:jopt-simple",
        "org.apache.commons:commons-lang3",
        "org.apache.logging.log4j:log4j-api",
        "org.apache.logging.log4j:log4j-core",
        "org.apache.logging.log4j:log4j-slf4j2-impl",
        "org.joml:joml",
        "org.lz4:lz4-java",
        "org.ow2.asm:asm",
        "org.slf4j:slf4j-api"
    )

    dependencies {
        // Provided by the game or Fabric
        mojangProvided.forEach {
            exclude(dependency("$it:.*"))
        }

        // Kotlin
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib:.*"))

        // Compile only
        exclude(dependency("org.jetbrains:annotations:.*"))
        exclude(dependency("com.intellij:annotations:.*"))

        // Provided by Adventure platform mod
        exclude(dependency("net.kyori:adventure-api:.*"))
        exclude(dependency("net.kyori:adventure-key:.*"))
        exclude(dependency("net.kyori:adventure-platform-api:.*"))
        exclude(dependency("net.kyori:adventure-text-logger-slf4j:.*"))
        exclude(dependency("net.kyori:adventure-text-minimessage:.*"))
        exclude(dependency("net.kyori:adventure-text-serializer-ansi:.*"))
        exclude(dependency("net.kyori:adventure-text-serializer-gson:.*"))
        exclude(dependency("net.kyori:adventure-text-serializer-json:.*"))
        exclude(dependency("net.kyori:adventure-text-serializer-plain:.*"))
        exclude(dependency("net.kyori:ansi:.*"))
        exclude(dependency("net.kyori:examination-api:.*"))
        exclude(dependency("net.kyori:examination-string:.*"))
        exclude(dependency("net.kyori:option:.*"))
    }

    exclude("licenses/**/*")
    exclude("META-INF/maven/**/*")
    exclude("*.html")
    exclude("META-INF/NOTICE.txt")
    exclude("META-INF/NOTICE")
    exclude("mojang-translations/**/*")
    exclude("META-INF/LICENSE.txt")
    exclude("META-INF/LICENSE")
    exclude("META-INF/DEPENDENCIES")
    exclude("INFO_BIN")
    exclude("INFO_SRC")
    exclude("LICENSE")
    exclude("README")

    // Seems bundled from log4j, yet we don't use modules
    exclude("module-info.class")
}
