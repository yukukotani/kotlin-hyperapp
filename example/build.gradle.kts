import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("org.jetbrains.kotlin.js")
}

val kotlinVersion: String by extra
val hyperappVersion: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-js"))
    compile(project(":kotlin-hyperapp"))
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

tasks.build {
    dependsOn(tasks.findByPath(":kotlin-hyperapp:build"))
}
