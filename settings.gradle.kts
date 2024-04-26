rootProject.name = "drill-integration"

pluginManagement {
    val kotlinVersion: String by extra
    val grgitVersion: String by extra
    val licenseVersion: String by extra
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("org.ajoberstar.grgit") version grgitVersion
        id("com.github.hierynomus.license") version licenseVersion
    }
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":drill-common")
include(":drill-gradle-plugin")
//include(":drill-maven-plugin")
//include(":drill-cli")
project(":drill-common").projectDir = file("common")
project(":drill-gradle-plugin").projectDir = file("gradle-plugin")
//project(":drill-maven-plugin").projectDir = file("maven-plugin")
//project(":drill-cli").projectDir = file("cli")
