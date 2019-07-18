plugins {
    id("org.jetbrains.kotlin.js") version "1.3.41"
    id("com.jfrog.bintray") version "1.8.4" apply false
    id("net.researchgate.release") version "2.8.1"
}

allprojects {
    group = rootProject.group
    version = rootProject.version
}

release {
    failOnCommitNeeded = false
    failOnUnversionedFiles = false
    buildTasks = listOf<String>()
}
