import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("org.jetbrains.kotlin.js")
    id("com.moowork.node")
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

task<Copy>("preparePackage") {
    from(".")
    include("package.json.template")
    expand(
        "version" to version,
        "kotlin_version" to kotlinVersion,
        "hyperapp_version" to hyperappVersion
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
