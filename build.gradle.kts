plugins {
    id("org.jetbrains.kotlin.js") version "1.3.41"
    id("com.moowork.node") version "1.3.1"
    id("com.jfrog.bintray") version "1.8.4" apply false
}

allprojects {
    group = "land.mog"
    version = "1.0.1"
    
    ext["kotlinVersion"] = "1.3.41"
    ext["hyperappVersion"] = "1.2.10"
}
