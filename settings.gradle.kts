/**
 * settings.gradle.kts is used for bootstrapping a build.
 *
 * Here, we set up a repository configuration for plugins, and one for other artifacts.
 * The reason there are property declarations in both sections, is because pluginManagement
 * is special; it exits "before" the settings.gradle.kts file, just like the "plugins"
 * section in a build.gradle.kts file (this used to be done with the deprecated "buildscript"
 * section). Thus, the "pluginManagement" scope does not have access to anything else in the
 * file.
 *
 * The default system is set up to use published artifacts from the XTC GitHub Maven package
 * repository: https://github.com/orgs/xtclang/packages?repo_name=xvm
 *
 * Currently, both release and snapshot artifacts for the XDK and the XTC Plugin are published
 * with intermittent intervals to the GitHub Maven Package Repo for xtclang.org. For unknown
 * reasons, Microsoft requires valid credentials to access that repo, even for public artifacts,
 * which typically should just be as simple as it is accessing public GitHub source code repositories,
 * from any Internet café, i.e. with wget or curl. They have been asked to fix this for ~five years,
 * but GitHub just recently deleted the (very long) user forum thread where this discussion was taking
 * place, so it's not going to happen soon. Accept it, and set this up once to use this way of
 * retrieving XTC artifacts from GitHub.
 *
 * As soon as we have our next public release, we will publish that on gradlePluginPortal and
 * mavenCentral, so if you are an XTC user who doesn't want to work with bleeding edge snapshots,
 * or if you are content to build the XDK locally, you can forget about GitHub artifact credentials,
 * after that, and happily just refer to those to repositories in your settings. You can also
 * use the process for building your own XDK and publishing (locally) your own artifacts, which
 * is described in the README.md as well.
 *
 * To get the values for the "gitHubToken" property used in this file, go to GitHub, and create
 * a token with read:package privilege. Instructions for how to create a personal access token for
 * repositories to which you already have access, and their packages, go to your GitHub user profile,
 * click "Settings" and follow the instructions at:
 *
 *    https://docs.github.com/en/packages/learn-github-packages/about-permissions-for-github-packages
 *
 * Once you have a token privileged enough to read packages, cut and paste it into your
 * $GRADLE_USER_HOME/gradle.properties.
 *
 * The properties referred by settings delegate below, actually provide a lazy Gradle
 * property value, for a particular property name. Properties are resolved from the
 * "gradle.properties" file your repository root, or from a "gradle.properties" file in your
 * $GRADLE_USER_HOME directory. They can also be passed with the "-Pkey=value argument" to the
 * ./gradlew command line (for example "./gradlew -PgitHubUser=cpurdy -PgitHubToken=<secret>,"),
 * or finally, set as environment variables with ORG_GRADLE_PROJECT_ prefixes.
 * All of these methods are described in the Gradle user guide.
 *
 * We recommend you put your "gitHubUser" and "gitHubToken" property definitions in the file
 * "$GRADLE_USER_HOME/gradle.properties", outside the project, as is customary for secrets.
 * (It's not much of a secret if you accidentally push a token that allows to ,*shock horror*,
 * download a publicly available zip file, that you can grab with Google Chrome, but if
 * GitHub says it is sensitive, that particular kool must be swallowed).
 *
 * While we will publish our first Maven ecosystem release of XDK and the XTC Plugin to
 * gradlePluginPortal and mavenCentral very soon now, we may choose to go on publishing SNAPSHOT
 * releases to just the GitHub package repository for quite a bit longer. The project is still
 * in flux, and pre-1.0, so we may initially want to republish bleeding edge bits at every commit.
 * Even you are only interested in working with XTC release publications, it's very much a good
 * idea to be able to hook your XTC application up to our GitHub Maven Repository in the future
 * as well. And you only have to do this once.
 */
pluginManagement {
    val xtcVersion: String by settings

    repositories {
        val mavenLocalRepo: String? by settings
        val xtclangGitHubRepo: String? by settings

        val gitHubUser: String? by settings
        val gitHubToken: String? by settings
        val gitHubUrl: String by settings

        println("*** Plugin: mavenLocal=$mavenLocalRepo, xtclangGitHubRepo=$xtclangGitHubRepo, version: $xtcVersion")
        if (mavenLocalRepo != "true" && xtclangGitHubRepo != "true") {
            throw GradleException("Error: either or both of mavenResolveFromMavenLocal and mavenResolveFromXtcGitHub must be set.")
        }

        if (xtclangGitHubRepo == "true") {
            println("Adding github repo: gitHubUser=$gitHubUser, gitHubUrl=$gitHubUrl")
            maven {
                url = uri(gitHubUrl)
                credentials {
                    username = gitHubUser
                    password = gitHubToken
                }
            }
        }

        if (mavenLocalRepo == "true") {
            // Define mavenLocal as an artifact repository (disabled by default)
            mavenLocal()
        }

        // Define Gradle Plugin Portal as a plugin repository
        gradlePluginPortal()
    }

    plugins {
        id("org.xtclang.xtc-plugin") version xtcVersion
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Define XTC org GitHub Maven as a plugin repository
        val mavenLocalRepo: String? by settings
        val xtclangGitHubRepo: String? by settings

        val gitHubUser: String? by settings
        val gitHubToken: String? by settings
        val gitHubUrl: String by settings

        println("*** Repos: mavenLocal=$mavenLocalRepo, xtclangGitHubRepo=$xtclangGitHubRepo")
        if (mavenLocalRepo != "true" && xtclangGitHubRepo != "true") {
            throw GradleException("Error: either or both of mavenResolveFromMavenLocal and mavenResolveFromXtcGitHub must be set.")
        }

        if (xtclangGitHubRepo == "true") {
            maven {
                url = uri(gitHubUrl)
                credentials {
                    username = gitHubUser
                    password = gitHubToken
                }
            }
        }

        if (mavenLocalRepo == "true") {
            // Define mavenLocal as an artifact repository (disabled by default)
            mavenLocal()
        }
    }
}

// Set the name of the main project.
rootProject.name = "HelloXtc"

// Point out the build file for the main project.
include("hello-xtc")
