import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.util.Date

plugins {
    id("org.jetbrains.kotlin.js")
    id("com.moowork.node")
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
        }
    }
}

val preparePackageJson = task<Copy>("preparePackageJson") {
    from(".")
    include("package.json.template")
        .expand(
            "version" to version,
            "hyperapp_version" to hyperappVersion
        )
    rename {
        it.replace(".template", "")
    }
    into(".")
}

tasks.npmInstall {
    dependsOn(preparePackageJson)
}

val assembleJsLib = task<Copy>("assembleJsLib") {
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

val prepareNpmPackage = task<Copy>("prepareNpmPackage") {
    from("./build/js") {
        into("./dist")
    }
    into("./build/npm")

    dependsOn(assembleJsLib, tasks.npmInstall)
}

tasks.build {
    finalizedBy(prepareNpmPackage)
}

val npmPublish = tasks.getByName<NpmTask>("npm_publish") {
    setArgs(listOf("--access", "public"))
    setExecOverrides(closureOf<Any> {
        setWorkingDir("./build/npm")
    })
    
    dependsOn("build")
}

tasks.publish {
    finalizedBy(tasks.bintrayUpload, npmPublish)
}
