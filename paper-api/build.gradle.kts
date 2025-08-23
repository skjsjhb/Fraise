plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // https://github.com/PaperMC/Paper/blob/main/paper-api/build.gradle.kts
    // Tests, docs and generators are stripped

    api("com.google.guava:guava:33.3.1-jre")
    api("com.google.code.gson:gson:2.11.0")
    api("org.yaml:snakeyaml:2.2")
    api("org.joml:joml:1.10.8") {
        isTransitive = false
    }
    api("it.unimi.dsi:fastutil:8.5.15")
    api("org.apache.logging.log4j:log4j-api:2.24.1")
    api("org.slf4j:slf4j-api:2.0.16")
    api("com.mojang:brigadier:1.3.10")

    api("net.md-5:bungeecord-chat:1.21-R0.2-deprecated+build.21") {
        exclude("com.google.guava", "guava")
    }

    api(platform("net.kyori:adventure-bom:4.24.0"))
    api("net.kyori:adventure-api")
    api("net.kyori:adventure-text-minimessage")
    api("net.kyori:adventure-text-serializer-gson")
    api("net.kyori:adventure-text-serializer-legacy")
    api("net.kyori:adventure-text-serializer-plain")
    api("net.kyori:adventure-text-logger-slf4j")

    api("org.apache.maven:maven-resolver-provider:3.9.6")
    implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.9.18")
    implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.9.18")

    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnlyApi("org.checkerframework:checker-qual:3.49.2")

    api("org.jspecify:jspecify:1.0.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    // Paper just has so many classes marked for removal :(
    options.isWarnings = false
}
