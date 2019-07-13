pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.jetbrains.kotlin.js") {
                useVersion(requested.version)
            }
        }
    }
}

include("kotlin-hyperapp")
include("example")
findProject(":kotlin-hyperapp")?.name = "kotlin-hyperapp"
findProject(":example")?.name = "example"
