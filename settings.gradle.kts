pluginManagement {
    repositories {
        val orgXtcLangRepoPlugins: () -> MavenArtifactRepository by settings
        orgXtcLangRepoPlugins()
        mavenLocal() // also allow resolving XDK and XTC Plugin artifacts from mavenLocal (~/.m2/repository)
    }
    plugins {
        val xtcVersion: String by settings
        id("org.xtclang.xtc-plugin").version(xtcVersion)
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        val orgXtcLangRepo: () -> MavenArtifactRepository by settings
        orgXtcLangRepo()
        mavenLocal() // also allow resolving XDK and XTC Plugin artifacts from mavenLocal (~/.m2/repository)
    }
}

rootProject.name = "HelloXtc"

// Point out the build file for the main project.
include("hello-xtc")
