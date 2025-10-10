// Init script to show where dependencies are resolved from
// Usage: ./gradlew build -I gradle/init.d/show-resolution.gradle.kts
//
// This script is located in gradle/init.d/ following Gradle conventions, but is not
// automatically applied to avoid cluttering build output. Use the -I flag to apply it
// when you need to verify which repository resolved each dependency.

fun collectArtifactInfo(
    component: org.gradle.api.artifacts.result.ResolvedComponentResult,
    dependencies: org.gradle.api.artifacts.ResolvableDependencies
): Map<String, Any> {
    val id = component.moduleVersion!!

    @Suppress("DEPRECATION")
    val repo = (component as? org.gradle.api.internal.artifacts.result.ResolvedComponentResultInternal)
        ?.repositoryName ?: "unknown"

    // Collect dependencies
    val deps = component.dependencies.mapNotNull { dep ->
        (dep as? org.gradle.api.artifacts.result.ResolvedDependencyResult)?.selected?.moduleVersion?.let {
            "${it.group}:${it.name}:${it.version}"
        }
    }

    // Collect dependents
    val dependents = component.dependents.map { it.from.id.toString() }

    // Collect artifact files
    val fileInfo = runCatching {
        dependencies.artifacts.firstNotNullOfOrNull { artifact ->
            val artifactId = artifact.id.componentIdentifier
            if (artifactId.displayName.contains("${id.group}:${id.name}:${id.version}")) {
                val file = artifact.file
                linkedMapOf(
                    "path" to file.absolutePath,
                    "filename" to file.name,
                    "sizeBytes" to file.length(),
                    "sizeKB" to file.length() / 1024,
                    "type" to artifact.type.toString(),
                    "id" to artifact.id.toString(),
                    "lastModified" to java.util.Date(file.lastModified()).toString()
                )
            } else null
        }
    }.getOrElse { null } ?: "Not available in this resolution context"

    return linkedMapOf<String, Any>(
        "artifact" to "${id.group}:${id.name}:${id.version}",
        "repository" to repo,
        "componentId" to component.id.toString(),
        "selectionReason" to component.selectionReason.toString()
    ).apply {
        if (deps.isNotEmpty()) put("dependencies", deps)
        if (dependents.isNotEmpty()) put("dependents", dependents)
        put("file", fileInfo)
    }
}

fun printValue(value: Any, indent: String = "[artifact] ") {
    when (value) {
        is Map<*, *> -> value.forEach { (k, v) ->
            if (v is Map<*, *> || v is List<*>) {
                println("$indent$k:")
                printValue(v!!, "$indent  ")
            } else {
                println("$indent$k: $v")
            }
        }
        is List<*> -> value.forEach { item ->
            println("$indent- $item")
        }
        else -> println("$indent$value")
    }
}

fun printArtifactInfo(info: Map<String, Any>) {
    printValue(info)
}

gradle.addListener(object : org.gradle.api.artifacts.DependencyResolutionListener {
    override fun beforeResolve(dependencies: org.gradle.api.artifacts.ResolvableDependencies) {
        // No-op
    }

    override fun afterResolve(dependencies: org.gradle.api.artifacts.ResolvableDependencies) {
        try {
            dependencies.resolutionResult.allComponents
                .filter { it.moduleVersion?.group == "org.xtclang" }
                .map { collectArtifactInfo(it, dependencies) }
                .forEach {
                    printArtifactInfo(it)
                    println()
                }
        } catch (e: Exception) {
            println("*** Caught error during resolution listener: ${e.message}")
            e.printStackTrace()
        }
    }
})
