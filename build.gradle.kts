// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android)apply false
}

// Custom clean task to handle file system issues
tasks.register("cleanAll") {
    dependsOn("clean")
    doLast {
        // Force delete build directories that might be locked
        project.subprojects.forEach { subproject ->
            val buildDir = subproject.buildDir
            if (buildDir.exists()) {
                buildDir.deleteRecursively()
            }
        }
    }
}

subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-stdlib")) {
                useVersion("1.9.10")
            }
        }
    }
}