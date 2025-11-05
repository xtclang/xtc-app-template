# XTC Simple Hello World App Template

This template is a simple starting point for creating XTC applications.

## TL;DR

Run the greeting app:
```bash
./gradlew greet -PentityToGreet=YourName
```

Or just use the default:
```bash
./gradlew greet
```

## XTC Version Configuration

The XTC plugin and XDK version is managed in the Gradle version catalog at `gradle/libs.versions.toml`:

```toml
[versions]
xtc = "0.4.4-SNAPSHOT"
```

You can use either release versions (e.g., `0.4.3`) or snapshot versions (e.g., `0.4.4-SNAPSHOT`).

## Building and Running

Build the project:
```bash
./gradlew build
```

Run with a custom greeting:
```bash
./gradlew greet -PentityToGreet=Marcus
```

Or use an environment variable:
```bash
ORG_GRADLE_PROJECT_entityToGreet=Marcus ./gradlew greet
```

## Advanced Configuration

### Dependency Resolution

The build searches for XTC artifacts in this order:
1. Maven Central Snapshots (for SNAPSHOT versions)
2. Maven Central (for release versions)
3. Maven Local (`~/.m2/repository/`) as fallback

### Local Development

Set `localOnly=true` in `gradle.properties` to use only Maven Local for development against a local XVM build.

To build XVM locally:
```bash
git clone https://github.com/xtclang/xvm.git
cd xvm
./gradlew publishLocal
```

### Using GitHub Packages

To use GitHub Packages as a repository source for the XTC plugin and XDK, add the following to your `settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/xtclang/xvm")
            credentials {
                username = providers.gradleProperty("gpr.user")
                    .orElse(providers.environmentVariable("GITHUB_ACTOR")).get()
                password = providers.gradleProperty("gpr.token")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN")).get()
            }
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/xtclang/xvm")
            credentials {
                username = providers.gradleProperty("gpr.user")
                    .orElse(providers.environmentVariable("GITHUB_ACTOR")).get()
                password = providers.gradleProperty("gpr.token")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN")).get()
            }
        }
        mavenCentral()
    }
}
```

Then set your credentials either in `gradle.properties` (never commit this file with credentials!):
```properties
gpr.user=your-github-username
gpr.token=your-github-personal-access-token
```

Or use environment variables:
```bash
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=your-github-personal-access-token
./gradlew build
```

To create a GitHub Personal Access Token:
1. Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Select scope: `read:packages`
4. Generate and copy the token

### Refreshing Dependencies

Gradle caches resolved dependencies to improve build performance. However, when working with SNAPSHOT versions or local development, you may need to force Gradle to re-download artifacts.

#### Why Use --refresh-dependencies

Use `--refresh-dependencies` when:
- **Working with SNAPSHOT versions**: SNAPSHOTs can be updated at any time, and Gradle's cache may contain an older version
- **Local development**: After running `./gradlew publishLocal` in the XVM repository to update local artifacts
- **Switching between repositories**: When changing between Maven Local, Maven Central, and other repositories
- **Troubleshooting**: When you suspect cached dependencies are causing issues

#### What Happens During --refresh-dependencies

When you run with `--refresh-dependencies`, Gradle will:
1. **Bypass all caches**: Ignore both local and remote dependency caches
2. **Re-resolve metadata**: Download fresh POM files and metadata from all configured repositories
3. **Re-download artifacts**: Fetch new versions of the XTC plugin, XDK distribution, and javatools JAR
4. **Update checksums**: Verify integrity of all downloaded artifacts
5. **Rebuild dependency graph**: Recalculate the complete dependency tree

This is especially important when using `localOnly=true` after publishing changes locally:
```bash
# In the XVM repository
./gradlew publishLocal

# In your app template
./gradlew build --refresh-dependencies -PlocalOnly=true
```

Without `--refresh-dependencies`, Gradle may continue using older cached versions even after you've published new artifacts locally.

#### Force Refresh Example
```bash
./gradlew build --refresh-dependencies
```

### Verifying Dependency Sources

To see where dependencies are being resolved from:
```bash
./gradlew build -I gradle/init.d/show-resolution.gradle.kts
```