import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("org.jetbrains.kotlin.js")
    id("com.moowork.node")
}

val kotlin_version: String by extra
val hyperapp_version: String by extra

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

task<Copy>("preparePackage") {
    from(".")
    include("package.json.template")
    expand(
        "version" to version,
        "kotlin_version" to kotlin_version,
        "hyperapp_version" to hyperapp_version
    )
    rename {
        it.replace(".template", "")
    }
    into(".")
    
    finalizedBy("npm_run_build")
}

tasks.build {
    finalizedBy("preparePackage")
}
