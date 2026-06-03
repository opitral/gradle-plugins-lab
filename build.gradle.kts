plugins {
    id("java")
    application
    // Власний плагін з included build "build-logic"
    id("com.opitral.project-tools")
}

group = "com.opitral"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("com.opitral.App")
}

tasks.test {
    useJUnitPlatform()
}

// Task, оголошений безпосередньо в build.gradle.kts:
// пакує вихідні коди у zip-архів у build/dist.
tasks.register<Zip>("packageSources") {
    group = "distribution"
    description = "Пакує src/ у zip-архів із версією у назві"
    dependsOn(tasks.named("test"))

    from(layout.projectDirectory.dir("src"))
    archiveBaseName.set("${rootProject.name}-sources")
    archiveVersion.set(version.toString())
    destinationDirectory.set(layout.buildDirectory.dir("dist"))

    doLast {
        logger.lifecycle("Архів зібрано: ${archiveFile.get().asFile.path}")
    }
}
