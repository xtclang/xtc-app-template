/**
 * settings.gradle.kts is used for bootstrapping a build.
 *
 * Here, we set up a repository configuration for plugins, and one for other artifacts.
 * The reason there are property declarations in both sections, is because pluginManagement
 * is special; it exits "before" the settings.gradle.kts file, just like the "plugins"
 * section in a build.gradle.kts file (this used to be done with the deprecated "buildscript"
 * section). Thus, the pluginManagement scope does not have access to anything else in the
 * file.
 *
 * Currently, both release and snapshot artifacts for the XDK and the XTC Plugin are published
 * with intermittent intervals to the GitHub Maven Package Repo for xtclang.org. For unknown
 * reasons, Microsoft requires valid credentials to access that repo, even for public artifacts,
 * which typically should just be as simple as it is accessing public GitHub source code repositories,
 * i.e. from an Internet café with wget or curl. They have been asked to fix this for five years,
 * and GitHub just recently deleted the user forum thread where this discussion was taking place, so
 * it's not going to happen soon.
 *
 * As soon as we have our next public release, we will publish that on gradlePluginPortal and
 * mavenCentral, so if you are an XTC user who doesn't want to work with bleeding edge snapshots,
 * or if you are content to build the XDK locally, you can forget about GitHub artifact credentials
 * after that, and happily just refer to those to repositories in your settings.
 *
 * To get the values for the gitHubToken property, that we suggest you place in your $GRADLE_USER_HOME/
 * gradle.properties file, go to GitHub and create a personal access token with read:package privileges
 * to the XTC Maven repository: https://github.com/orgs/xtclang/packages?repo_name=xvm
 * Instructions for how to create a personal access token for repositories to which you already
 * have access, and their packages, go to your GitHub user profile, click "Settings" and
 * follow https://docs.github.com/en/packages/learn-github-packages/about-permissions-for-github-packages
 * Once you have a token privileged enough to read packages, cut and paste it into your
 * $GRADLE_USER_HOME/gradle.properties.
 *
 * The properties read from settings by delegate (e.g. gitHubUrl), actually read a property
 * with that name. Properties are resolved from gradle.properties, properties passed with the -P
 * option to the ./gradlew command line, e.g. ./gradlew -PgitHubUser=cpurdy -PgitHubToken=<secret>,
 * or finally, set as environment vars with ORG_GRADLE_PROJECT_ prefixes.
 * We recommend you put them in $GRADLE_USER_HOME/gradle.properties file, outside the project.
 * As we plan to keep daily snapshot releases there, it's probably the best idea, because you
 * have to do it once only, and you will have access to those properties in any Gradle project.
 */

pluginManagement {
    repositories {
        maven {
            val gitHubUrl: String by settings
            val gitHubUser: String by settings
            val gitHubToken: String by settings
            url = uri(gitHubUrl)
            credentials {
                username = gitHubUser
                password = gitHubToken
            }
        }
        if (providers.gradleProperty("allowMavenLocal").getOrElse("false").toBoolean()) {
            mavenLocal() // also look for XVM "/.gradlew publishLocal" artifacts in the maven local repo
        }
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
        val gitHubUrl: String by settings
        val gitHubUser: String by settings
        val gitHubToken: String by settings
        maven {
            url = uri(gitHubUrl)
            credentials {
                username = gitHubUser
                password = gitHubToken
            }
        }
        if (providers.gradleProperty("allowMavenLocal").getOrElse("false").toBoolean()) {
            mavenLocal() // also look for XVM "/.gradlew publishLocal" artifacts in the maven local repo
        }
    }
}

// Set the name of the main project.
rootProject.name = "HelloXtc"

// Point out the build file for the main project.
include("hello-xtc")
