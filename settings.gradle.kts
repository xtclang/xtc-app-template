/**
 * Settings configuration for XTC projects.
 *
 * Set localOnly=true in gradle.properties to use only mavenLocal for development.
 * Otherwise, resolves from Maven Central (releases) and Maven Central Snapshots (snapshots).
 */

pluginManagement {
    val localOnly: String by settings
    repositories {
        if (localOnly.toBoolean()) {
            mavenLocal { content { includeGroup("org.xtclang") } }
            gradlePluginPortal()
        } else {
            maven("https://central.sonatype.com/repository/maven-snapshots/") {
                mavenContent { snapshotsOnly() }
            }
            mavenCentral()
            gradlePluginPortal()
            mavenLocal { content { includeGroup("org.xtclang") } }
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
        if (localOnly.toBoolean()) {
            mavenLocal { content { includeGroup("org.xtclang") } }
        } else {
            maven("https://central.sonatype.com/repository/maven-snapshots/") {
                mavenContent { snapshotsOnly() }
            }
            mavenCentral()
            mavenLocal { content { includeGroup("org.xtclang") } }
        }
    }
}

rootProject.name = "HelloXtc"
include("hello-xtc")
