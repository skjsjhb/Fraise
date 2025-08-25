plugins {
    kotlin("jvm") version "2.2.10"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(project(":paper-api"))
}
