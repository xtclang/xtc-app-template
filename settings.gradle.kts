/**
 * Settings configuration for XTC projects.
 *
 * Set localOnly=true in gradle.properties to use only mavenLocal for development.
 * Otherwise, resolves from Maven Central (releases and snapshots).
 */

pluginManagement {
    val localOnly: String by settings
    repositories {
        // Add remote Maven repositories unless localOnly mode
        if (!localOnly.toBoolean()) {
            maven("https://central.sonatype.com/repository/maven-snapshots/") {
                mavenContent { snapshotsOnly() }
            }
            mavenCentral()
            gradlePluginPortal()
        }
        // Always check Maven Local last (or first in localOnly mode)
        mavenLocal()
        // Gradle Plugin Portal for standard plugins
        if (localOnly.toBoolean()) {
            gradlePluginPortal()
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    val localOnly: String by settings
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Add remote Maven repositories unless localOnly mode
        if (!localOnly.toBoolean()) {
            maven("https://central.sonatype.com/repository/maven-snapshots/") {
                mavenContent { snapshotsOnly() }
            }
            mavenCentral()
        }
        // Always include Maven Local
        mavenLocal()
    }
}

rootProject.name = "HelloXtc"
include("hello-xtc")
