# XTC Simple Hello World App Template

This template can be used to create a simple XTC app. It is a good starting point for new XTC apps.

## TL;DR - I just want to build this

### Set up the Environment

#### ... From a clone of the XVM repository

1) Execute `git clone https://github.com/xtclang/xvm.git <xvmdir>` (close the XVM repo to another place on 
   your dev machine)
2) In the `<xvmdir>` Execute `./gradlew publishLocal` to publish the XDK artifacts to the local maven repository
   on your dev machine (typically `$HOME/.m2`)

#### ... and/or as a self contained project

1) Understand why you need a GitHub personal access token (for now), to refer to XDK Maven artifacts, by reading the comment at the top of settings.gradle.kts in the repo root.
2) Set up a GitHub classic personal access token with read:package privileges for the `xtclang.org` Maven package repository on GitHub.
   
   Click your user Settings -> Developer Settings -> Personal Access Tokens -> Tokens (Classic) (or go 
   to: https://github.com/settings/tokens). Create an access token with at least `read:packages` privileges,
   if you don't have one already with that (and possibly more). Copy and paste the token to
   `$GRADLE_USER_HOME/gradle.properties` as a separate line on the form `gitHubToken=<token>`. While you are 
   in there, add a line with `gitHubUser=<your github username>` too. 

   *(This step will go away soon, at least for release artifacts (frequently updated snapshot artifacts may
    remain on GitHub), and you will soon be able to just configure your repositories in the settings file as
   `gradlePluginPortal()` and `mavenCentral()`)*

   `$GRADLE_USER_HOME`, referred to in this step, is typically set to be `<your home directory>/.gradle`.

### Building the app

```
./gradlew build [--scan] [--stacktrace]
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
