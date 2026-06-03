plugins {
    `java-gradle-plugin`
}

group = "com.opitral"

gradlePlugin {
    plugins {
        create("projectTools") {
            id = "com.opitral.project-tools"
            implementationClass = "com.opitral.ProjectToolsPlugin"
        }
    }
}
