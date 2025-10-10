import org.xtclang.plugin.tasks.XtcVersionTask

plugins {
    alias(libs.plugins.xtc)
}

// Warn if using localOnly mode
val localOnly: String by project
if (localOnly.toBoolean()) {
    logger.warn("WARNING: Will only use Maven Local for XTC plugin/XDK artifacts.")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get().toInt()))
    }
}

dependencies {
    xdkDistribution(libs.xdk)
}

xtcRun {
    // Set debug to "true" if you want the spawned runners to suspend and wait for a debugger to attach.
    debug = false
    // To run more modules in sequence, you can add more module sections after this one.
    module {
        moduleName = "HelloWorld"
        moduleArgs("Hello ", "there, ")
        moduleArg(providers.gradleProperty("entityToGreet").getOrElse("World"))
    }
}

// Print version information
val printVersionInfo by tasks.registering {
    val xtcVersionTask = tasks.named<XtcVersionTask>("xtcVersion")
    dependsOn(xtcVersionTask)
    val xdkVersion = xtcVersionTask.flatMap { it.xdkVersion }
    val semanticVersion = xtcVersionTask.flatMap { it.semanticVersion }
    doLast {
        println("XDK Version from task: ${xdkVersion.get()} (semantic version: ${semanticVersion.get()})")
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
    description = "Publish a greeting, directed to the contents of the 'entityToGreet' property. If no such property exists, we will greet 'World'."
    dependsOn(printVersionInfo, tasks.runXtc)
}
