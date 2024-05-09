import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.hierynomus.gradle.license.tasks.LicenseCheck
import com.hierynomus.gradle.license.tasks.LicenseFormat

plugins {
    `maven-publish`
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.github.hierynomus.license")
}

group = "com.epam.drill.integration"
version = rootProject.version

val kotlinxCoroutinesVersion: String by parent!!.extra

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        create("drill-integration-gradle-plugin") {
            id = "${group}.drill-gradle-plugin"
            implementationClass = "com.epam.drill.integration.gradle.DrillCiCdIntegrationGradlePlugin"
        }
    }
}

dependencies {
    compileOnly(gradleApi())
    compileOnly((kotlin("stdlib-jdk8")))
    compileOnly((kotlin("gradle-plugin")))
    implementation(project(":drill-common"))
    implementation(project(":drill-gitlab"))
    implementation(project(":drill-github"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinxCoroutinesVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

@Suppress("UNUSED_VARIABLE")
license {
    headerURI = URI("https://raw.githubusercontent.com/Drill4J/drill4j/develop/COPYRIGHT")
    val licenseFormatSources by tasks.registering(LicenseFormat::class) {
        source = fileTree("$projectDir/src").also {
            include("**/*.kt", "**/*.java", "**/*.groovy")
        }
    }
    val licenseCheckSources by tasks.registering(LicenseCheck::class) {
        source = fileTree("$projectDir/src").also {
            include("**/*.kt", "**/*.java", "**/*.groovy")
        }
    }
}
