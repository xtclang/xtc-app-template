plugins {
    id("org.xtclang.xtc-plugin")
}

dependencies {
    val xtcVersion: String by properties
    xdkDistribution("org.xtclang:xdk:$xtcVersion")
}

xtcRun {
    // To debug, do one of, or a combination of
    //   1) Set fork to "false", which makes the Gradle task execute in the build process. Run the "greet" task in debug configuration from the Gradle settings
    //   2) Set up Gradle debug attachment by uncommenting the org.gradle.debug property in gradle.properties. Attach to process from IntelliJ
    //   3) Run Gradle with --no-daemon, to get everything to happen in the build thread.
    //   4) Use the org.gradle.debug=true flag in your configuration. See the Gradle User Guide, and gradle.properties in the repo root for more info.
    fork = false // Set to "false" to launch xec in the build thread, so that we can seamlessly debug our way into it with the IDE.

    // To run more modules in sequence, you can add more module sections after this one.
    module {
        moduleName = "HelloWorld"
        moduleArgs("Hello ", "there, ")
        moduleArg(providers.gradleProperty("entityToGreet").getOrElse("World"))
    }
}

/**
 * Display a greeting. The task will greet "World" by default, but you can send
 * an argument to it, that will be read from the "entityToGreet" property, like so:
 *
 * ./gradle greet -PentityToGreet=Marcus
 *
 * If no property is provided, the task will check the System environment variable
 * "ORG_GRADLE_PROJECT_entityToGreet". If that is empty, we use the default, e.g:
 *
 * ORG_GRADLE_PROJECT_entityToGreet=Dima ./gradlew greet
 *
 * (The task name 'greet' is just an alias, for illustration purposes. You can call runXtc directly.)
 */
val greet by tasks.registering {
    group = "application"
    dependsOn(tasks.runXtc)
}
