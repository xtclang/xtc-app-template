# XTC Simple App Template

This template can be used to create a simple XTC app. It is a good starting point for new XTC apps.

## TL;DR - I just want to build this

1) "git clone git@github.com:xtclang/xvm.git <xvmdir>" (close the XVM repo to another place on your dev machine)
2) In the <xvmdir>, do "git checkout xtc-plugin" (This will be merged to master soon, and you won't need this step)
3) In the <xvmdir>, execute "./gradlew installInitScripts (this will set up a config under $GRADLE_USER_HOME
   that provides read-only, package-only credentials, and lets the project resolve our GitHub Maven Artifact Repo.)"

AND/OR, it's a good exercise to do both, if you plan to work with the XDK and not just with XTC projects.

3) Build and publish the XDK yourself, including artifacts; in the <xvmdir>, do "./gradlew publishLocal". 
   This places the XTC Plugin and XDK publication in your mavenLocal repository (typically ~/.m2)

### Building the app

```
./gradle build
```

### Running the app 

Default config:
```
./gradlew greet 
```

The default entity to be greeted is "World", but you can specify one with the entityToBeGreeted
property, like:

```
./gradlew greet [-PentityToGreet=<string>]
```

Or in its equivalent environment variable:

```
ORG_GRADLE_PROJECT_entityToGreet ./gradlew greet
```

#### Experiments

Try running a build scan with the --scan parameter at the end of the ./gradlew command line and
browse an X-ray of what is going on inside the build process.

You can use sourceSets just like with the Java plugin. You can add new folders, resource folders, 
and configure the xtcCompile and xtcRun extensions to do anything that is possible to tell the
existing "xcc" and "xec" launchers.
