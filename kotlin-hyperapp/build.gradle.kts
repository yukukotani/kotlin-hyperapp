import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("org.jetbrains.kotlin.js")
    id("com.moowork.node")
}

val hyperapp_version: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-js"))
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

task<Copy>("preparePackageJson") {
    from(".")
    include("package.json.template")
    expand(
        "version" to version,
        "hyperapp_version" to hyperapp_version
    )
    rename {
        it.replace(".template", "")
    }
    into(".")
}

task<Copy>("preparePackage") {
    from("./build/js") {
        into("./dist")
    }
    into("./build/npm")
    
    dependsOn("assembleJsLib", "npmInstall")
}

task<Copy>("assembleJsLib") {
    from(zipTree(File("$buildDir/libs/${project.name}-${project.version}.jar"))) {
        include { fileTreeElement ->
            val path = fileTreeElement.path
            (path.endsWith(".js") || path.endsWith(".js.map")) && (path.startsWith("META-INF/resources/") ||
                    !path.startsWith("META-INF/"))
        }
        rename {
            if (it.startsWith("kotlin-hyperapp-")) it.replaceFirst("kotlin-hyperapp-", "")
            else it
        }
    }
    from(tasks.withType<ProcessResources>().map { it.destinationDir })
    into("$buildDir/js")

    dependsOn("assemble")
}

tasks.build {
    finalizedBy("preparePackage")
}

tasks.npmInstall {
    dependsOn("preparePackageJson")
}

tasks.getByName<NpmTask>("npm_publish") {
    setArgs(listOf("--access", "public"))
    setExecOverrides(closureOf<Any> {
        setWorkingDir("./build/npm")
    })
    
    dependsOn("build")
}
