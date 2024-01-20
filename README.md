# XTC Simple App Template

This template can be used to create a simple XTC app. It is a good starting point for new XTC apps.

## TL;DR - I just want to build this

1) git clone git@github.com:xtclang/xvm.git (close the XVM repo to another place on your dev machine)
2) In the xvm repo root, execute ./gradlew installInitScripts (this will set up a config under $GRADLE_USER_HOME
   that provides read-only, package-only credentials, and lets the project resolve our GitHub Maven Artifact Repo.
3) In THIS repo, in the root, do ./gradlew greet

Alternatively:

Replace step 2) in the above descrption with.

2) In the xvm repo root, execute ./gradlew publishLocal (this will publish the latest artifacts in your local Maven repository)

### Bootstrapping repos (will go away later) 

There is some bootstrapping required for the syntactic sugar involved retrieving the XTC plugin and XDK 
artifacts from the XTC Org GitHub package repo, which inexplicably requires credentials for everything, even
package reads. To bootstrap these settings, use the 

./gradlew installInitScripts 

Command from the XDK. This only has to done once, and will install code to resolve the "orgXtcLang()" repositories
for plugins and XDK artifacts, by placing some bootstrapping code in GRADLE_USER_HOME/init.d.

You can also install the XDK artifacts of the correct version from the XDK repo and use "mavenLocal()"

./gradlew publishLocal

This problem will go away after we have validated the org.xtclang domain so that we can provide
artifacts on gradlePluginPortal() and mavenCentral() respectively.

### Building the app

```
./gradle build
```

### Running the app 

```
./gradlew runXtc [-PentityToBeGreeted=<string>]
```

#### Experiments

You can use sourceSets just like with the Java plugin. You can add new folders, resource folders, 
and configure the xtcCompile and xtcRun extensions to do anything that is possible to tell the
existing "xcc" and "xec" launchers.
