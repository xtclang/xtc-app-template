// Init script to show where dependencies are resolved from
// Usage: ./gradlew build -I gradle/init.d/show-resolution.gradle.kts
//
// This script is located in gradle/init.d/ following Gradle conventions, but is not
// automatically applied to avoid cluttering build output. Use the -I flag to apply it
// when you need to verify which repository resolved each dependency.

gradle.addListener(object : org.gradle.api.artifacts.DependencyResolutionListener {
    override fun beforeResolve(dependencies: org.gradle.api.artifacts.ResolvableDependencies) {
        // No-op
    }

    override fun afterResolve(dependencies: org.gradle.api.artifacts.ResolvableDependencies) {
        try {
            dependencies.resolutionResult.allComponents.forEach { component ->
                val id = component.moduleVersion
                if (id?.group == "org.xtclang") {
                    val repo = (component as? org.gradle.api.internal.artifacts.result.ResolvedComponentResultInternal)
                        ?.repositoryName ?: "unknown"
                    println("*** Resolved: ${id.group}:${id.name}:${id.version} from repository: $repo")
                }
            }
        } catch (e: Exception) {
            println("*** Caught error during resolution listener: ${e.message}")
        }
    }
})
