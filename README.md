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

### Refreshing Dependencies

To force Gradle to re-resolve dependencies:
```bash
./gradlew build --refresh-dependencies
```

### Verifying Dependency Sources

To see where dependencies are being resolved from:
```bash
./gradlew build -I gradle/init.d/show-resolution.gradle.kts
```