plugins {
    id("org.xtclang.xtc-plugin")
}

dependencies {
    val xtcVersion: String by properties
    xdkZip("org.xtclang:xdk:$xtcVersion")
}

xtcRun {
    moduleName("HelloWorld")
}
