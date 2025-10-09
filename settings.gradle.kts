/**
 * settings.gradle.kts is used for bootstrapping a build.
 *
 * This configuration uses Maven Central for release artifacts and Maven Snapshots
 * for snapshot artifacts.
 */
pluginManagement {
    val localOnly: String by settings

    repositories {
        if (localOnly.toBoolean()) {
            // Use only Maven Local for XTC plugin/XDK, but keep Plugin Portal for Gradle plugins
            println("WARNING: Will only use Maven Local for XTC plugin/XDK artifacts.")
            mavenLocal()
            gradlePluginPortal()
            return@repositories
        }
        // Maven Central Snapshots for snapshot artifacts (check first for SNAPSHOT versions)
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
            mavenContent {
                snapshotsOnly()
            }
        }
        mavenCentral() // Maven Central for release XDK artifacts
        gradlePluginPortal() // Gradle Plugin Portal for release plugin artifacts
        mavenLocal() // Maven Local for local development (checked last)
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    val localOnly: String by settings

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        if (localOnly.toBoolean()) {
            // Use only Maven Local for local development
            mavenLocal()
            return@repositories
        }
        // Maven Central Snapshots for snapshot artifacts (check first for SNAPSHOT versions)
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
            mavenContent {
                snapshotsOnly()
            }
        }
        // Maven Central for release artifacts
        mavenCentral()

        // Maven Local for local development (checked last)
        mavenLocal()
    }
}

// Set the name of the main project.
rootProject.name = "HelloXtc"

// Point out the build file for the main project.
include("hello-xtc")
