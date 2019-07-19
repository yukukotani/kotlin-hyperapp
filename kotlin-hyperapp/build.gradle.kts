import org.jetbrains.kotlin.gradle.targets.js.nodejs.nodeJs
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.util.*

plugins {
    id("org.jetbrains.kotlin.js")
    id("maven-publish")
    id("com.jfrog.bintray")
}

val hyperappVersion: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-js"))
}

nodeJs.packageJson {
    this.main = null
    this.dependencies["hyperapp"] = hyperappVersion
}

kotlin.target.browser {
    configure<SourceSetContainer> {
        tasks.withType<Kotlin2JsCompile> {
            kotlinOptions {
                sourceMap = true
                moduleKind = "commonjs"
                metaInfo = true
            }
        }
    }
}

bintray {
    user = project.findProperty("bintray_user")?.toString() ?: ""
    key = project.findProperty("bintray_key")?.toString() ?: ""
    setPublications("maven")
    setConfigurations("archives")
    
    pkg = pkg.apply { 
        repo = "maven"
        name = "kotlin-hyperapp"
        setLicenses("Apache-2.0")
        version = version.apply {
            name = project.version.toString()
            vcsTag = project.version.toString()
            released = Date().toString()
        }
    }
}

publishing {
    publications {
        register("maven", MavenPublication::class) {
            from(components.getByName("kotlin"))
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            
            artifact(tasks.getByName("kotlinSourcesJar"))
        }
    }
}

tasks.JsJar {
    from("../build/js/packages/kotlin-hyperapp/package.json")
}

tasks.bintrayUpload {
    dependsOn("build")
}

tasks.publish {
    finalizedBy(tasks.bintrayUpload)
}
