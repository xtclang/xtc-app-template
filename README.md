# XTC Simple Hello World App Template

This template can be used to create a simple XTC app. It is a good starting point for new XTC apps.

## TL;DR - I just want to build this

### Set up the Environment

#### XTC Version Configuration

The XTC plugin and XDK version is specified in `gradle.properties`:

```properties
xtcVersion=0.4.4-SNAPSHOT
```

You can use either:
- **Release versions**: `xtcVersion=0.4.3` (specific released version)
- **Snapshot versions**: `xtcVersion=0.4.4-SNAPSHOT` (latest development snapshot)

#### Dependency Resolution Order

The build is configured to search for XTC artifacts in the following order:

1. **Maven Central Snapshots** (`https://central.sonatype.com/repository/maven-snapshots/`)
   - Checked first for SNAPSHOT versions
   - Provides the latest published development builds
   - Updated automatically when new snapshots are published

2. **Maven Central** (`mavenCentral()`)
   - Used for release versions
   - Official releases of XTC artifacts

3. **mavenLocal** (`~/.m2/repository/`) - Local Maven repository
   - Checked last as a fallback
   - Used when you've built and published the XVM repository locally using `./gradlew publishLocal`
   - Useful for testing local changes before they're published

This configuration prioritizes published artifacts while still allowing local development builds as a fallback.

#### Working with a Local XVM Repository Build (Optional)

If you want to develop against a local build of the XVM repository:

1) Execute `git clone https://github.com/xtclang/xvm.git <xvmdir>`
2) In `<xvmdir>`, execute `./gradlew publishLocal` to publish the XDK artifacts to your local maven repository (typically `$HOME/.m2`)
3) Since remote snapshots are checked first, your local build will only be used if it's not found remotely

#### Forcing Dependency Re-resolution

Gradle caches dependency resolution results, including which repository was used. You may need to refresh dependencies when:

- **Switching between local and remote builds**: After publishing locally with `./gradlew publishLocal`, or after a new snapshot is published remotely
- **Getting the latest snapshot**: When you know a newer snapshot has been published to Maven Central Snapshots
- **Troubleshooting dependency issues**: When dependencies seem stale or you're getting unexpected versions

To force Gradle to re-resolve dependencies and check all repositories:

```bash
./gradlew build --refresh-dependencies
```

This will:
- Clear Gradle's cached dependency metadata
- Re-check all configured repositories in order
- Download the latest snapshots if available

You can combine this with the resolution verification script to see what's being resolved:

```bash
./gradlew build --refresh-dependencies -I gradle/init.d/show-resolution.gradle.kts
```

### Building the app

```
./gradlew build [--scan] [--stacktrace]
```

### Verifying Dependency Resolution Sources

To see where the XTC plugin and XDK dependencies are being resolved from (mavenLocal, mavenCentral, or Maven Central Snapshots), you can run any Gradle command with the included init script:

```bash
./gradlew build -I gradle/init.d/show-resolution.gradle.kts
```

The `-I` flag tells Gradle to apply the init script for that specific build. This will output lines showing the repository source for each XTC dependency, for example:

```
*** Resolved: org.xtclang:xtc-plugin:0.4.4-SNAPSHOT from repository: maven
*** Resolved: org.xtclang:xdk:0.4.4-SNAPSHOT from repository: maven
```

This is useful when working with both local XVM repository builds and published artifacts to verify which version is being used.

**Note**: The init script is located in `gradle/init.d/` but is intentionally not applied automatically to avoid cluttering normal build output. Use the `-I` flag when you need to verify dependency sources.

### Running the app 

Default config:
```
./gradlew greet 
```

The default entity to be greeted is "World", but you can specify one with the `entityToGreet`
property, like:

```
./gradlew greet [-PentityToGreet=<string>]
```

Or in its equivalent environment variable:

```
ORG_GRADLE_PROJECT_entityToGreet=<string> ./gradlew greet
```

#### Experiments

Try running a build scan with the `--scan` parameter at the end of the `./gradlew` command line and
browse an X-ray of what is going on inside the build process.

You can use source sets just like with the Java plugin. You can add other source set folders, 
with custom names, resource folders, and configure the `xtcCompile` and `xtcRun` build DSL sections
to do anything  that is possible to tell the existing `xcc` and `xec` launchers.

### How to build this in IntelliJ (optional)

Once you have followed the steps above, you can also import this app into IntelliJ (or any other Gradle
aware IDE). For IntelliJ specifically, select "File -> Open -> <repo root for this repo>". IntelliJ
may ask you of you trust this project, to which you have to reply that you do.

The project is compiled through the Gradle wrapper, which is a common mechanism 
installation of Gradle or any of its helper apps. It just requires an old bootstrap JDK installation,
which can be Java 8 ir later. The Gradle Wrapper script will ensure you have the correct version
of Gradle and all its dependencies, without modifying any state on your system outside this repository
and its build.

On the top right of the screen, open the Gradle Settings. You should see an icon that looks like a
wrench to the right of top of the gradle window. Click it to open the Gradle settings, and confirm
that your project is using the Gradle Wrapper style build, and that it uses any JDK version 21 or better.
You can also select to use IDEA for building and running your project, instead of Gradle, which will
give you better incremental updates when you change something, but let's do one thing at a time.
If everything seems to work, exit the Gradle settings and click the "Reload Gradle" icon (the two
circular arrows) at the top left of the Gradle settings. You should be good to go.

However, these settings are recommended
   * Use IDEA for compiling and testing instead of Gradle (once you have a green build)
   * Compiler -> Incrementally rebuild project
   * Compiler -> Build independent modules in parallel
   * Compiler -> Be generous to the Gradle daemons and give them more than the 700 MB of memory they have by default, to run faster.
