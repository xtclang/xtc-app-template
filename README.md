# XTC Simple Hello World App Template

This template can be used to create a simple XTC app. It is a good starting point for new XTC apps.

## TL;DR - I just want to build this

1) `git clone https://github.com/xtclang/xvm.git <xvmdir>` (close the XVM repo to another place on your dev machine)
2) In the `<xvmdir>`, do `git checkout xtc-plugin` (This will be merged to master soon, and you won't need this step)
3) In the `<xvmdir>`, execute `./gradlew installInitScripts` (this will set up a config under $GRADLE_USER_HOME
   that provides read-only, package-only credentials, and lets the project resolve our GitHub Maven Artifact
   repo.) *
   
AND/OR, it's a good exercise to do both, if you plan to work with the XDK and not just with XTC projects:

3) Build and publish the XDK yourself, including artifacts; in the `<xvmdir>`, do `./gradlew publishLocal`. 
   This places the XTC Plugin and XDK publication in your mavenLocal repository (typically `$HOME/.m2`)

### Building the app

```
./gradlew build [--scan]
```

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

You can use source sets just like with the Java plugin. You can add other source set folders folders, 
with custom names, resource folders, and configure the `xtcCompile` and `xtcRun` build DSL sections
to do anything  that is possible to tell the existing `xcc` and `xec` launchers.

#### Footnotes

 * *Incredibly, you need a GitHub username and personal access token with read privileges to access files
   in the GitHub package repositories. Curl:ing anything from any public source code is fine, but reading public
   packages are apparently extremely problematic. There existed a ~5 year old thread with a three digitl number
   of user comments requesting to be able to download packages without credentials, but it was ignore. About
   a week ago, GitHub just deleted the thread, so it can be assumed that this is not something they intend to
   find, for unknown reasons. We do have credentials for gradlePluginPortal() and mavenCentral() and we will
   make our releases available there ASAP, as soon as the Gradle/Maven XTC plugin has been merged into master.)"*

