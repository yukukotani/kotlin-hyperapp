plugins {
    id("org.jetbrains.kotlin.js") version "1.3.41"
    id("com.moowork.node") version "1.3.1"
}

allprojects {
    group = "land.mog"
    version = "1.0.1"
    
    ext["kotlin_version"] = "1.3.41"
    ext["hyperapp_version"] = "2.1.0"
}
