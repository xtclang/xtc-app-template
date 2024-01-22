# XTC Simple Hello World App Template

This template can be used to create a simple XTC app. It is a good starting point for new XTC apps.

## TL;DR - I just want to build this

1) Understand why you need a GitHub personal access token (for now), to refer to XDK Maven artifacts, by reading   
   the comment at the top of settings.gradle.kts in the repo root.
2) Set up a GitHub classic personal access token with read:package privileges for the xtclang.org Maven
   package repository on GitHub. Click your user Settings -> Developer Settings -> Personal Access Tokens ->
   Tokens (Classic) (or go to: https://github.com/settings/tokens). Create an access token with `read:packages`
   access, if you don't have one already with that (and possibly more). Copy and paste the token to
   `$HOME/gradle.properties` as a separate line that looks like `gitHubToken=<token>`. While you are in there,
   Add a line with `gitHubUser=<your github username>` too. (This step will go away soon, at least for release
   artifacts (frequently updated snapshot artifacts may remain at GitHub), and you should be able to just 
   configure gradlePluginPortal and mavenCentral in your projects to download our releases).

AND/OR:

1) Execute `git clone https://github.com/xtclang/xvm.git <xvmdir>` (close the XVM repo to another place on your dev machine)
2) In the `<xvmdir>`, do `git checkout xtc-plugin` (This will be merged to master soon, and you won't need this step)
3) In the `<xvmdir>` Execute `./gradlew publishLocal` to publish the XDK artifacts to the local maven repository
   on your dev machine (typically `$HOME/.m2`)

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

