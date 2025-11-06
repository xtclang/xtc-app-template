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

Then run your build with:
```bash
./gradlew greet -PlocalOnly=true
```

### How Gradle Refreshes Dependencies

Understanding how Gradle handles dependency updates is important, especially when working with SNAPSHOT versions or local development.

#### SNAPSHOT vs Release Versions

**SNAPSHOT versions** (e.g., `0.4.4-SNAPSHOT`):
- Treated as "changing" dependencies that can be updated at any time
- Gradle automatically checks for updates from **remote** Maven repositories (like Maven Central Snapshots)
- Default cache TTL: 24 hours for remote repositories
- **Maven Local is an exception**: Gradle treats Maven Local as a local file cache and does NOT automatically refresh SNAPSHOTs from it

**Release versions** (e.g., `0.4.3`):
- Treated as immutable - once resolved, Gradle assumes they never change
- Cached indefinitely unless explicitly refreshed
- Gradle will not check for updates unless forced with `--refresh-dependencies`

#### When to Use --refresh-dependencies

Use `--refresh-dependencies` to force Gradle to bypass all caches and re-resolve dependencies:

```bash
./gradlew build --refresh-dependencies
```

This is necessary when:
- **Local development with Maven Local**: After running `./gradlew publishLocal` in the XVM repository
- **Troubleshooting**: When you suspect stale cached dependencies are causing issues
- **Switching repositories**: After changing repository configurations

Example workflow for local XVM development:
```bash
# In the XVM repository - make changes and publish
cd xvm
./gradlew publishLocal

# In your app - use the fresh artifacts
cd your-app
./gradlew greet -PlocalOnly=true --refresh-dependencies
```

**Note**: Once you've used `--refresh-dependencies` once after publishing, subsequent builds will pick up the new version automatically (even without the flag) because Gradle now has the correct metadata cached.

### Using Alternative Maven Repositories

This template supports standard Gradle Maven repository configuration. You can add any Maven-compatible repository to `settings.gradle.kts`. See [Appendix: GitHub Packages Example](#appendix-github-packages-example) for a complete example.

### Verifying Dependency Sources

To see where dependencies are being resolved from:
```bash
./gradlew build -I gradle/init.d/show-resolution.gradle.kts
```

---

## Appendix: GitHub Packages Example

This template supports any Maven-compatible repository. Here's an example of configuring GitHub Packages as a repository source for the XTC plugin and XDK.

Add the following to your `settings.gradle.kts`:

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