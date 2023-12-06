pluginManagement {
    repositories {
        val orgXtcLangRepoPlugins: () -> MavenArtifactRepository by settings
        orgXtcLangRepoPlugins()
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
    }
}

rootProject.name = "HelloXtc"
include("hello-xtc")
