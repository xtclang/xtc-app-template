plugins {
    id("org.xtclang.xtc-plugin")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

dependencies {
    val xtcVersion: String by properties
    xdkDistribution("org.xtclang:xdk:$xtcVersion")
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
    dependsOn(tasks.runXtc)
}
