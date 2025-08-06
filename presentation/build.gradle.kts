plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.madrid.presentation"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        resourceConfigurations.plus(listOf("en","ar"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    android {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.compiler.get()
        }
    }
}

dependencies {
    implementation(project(":designSystem"))
    implementation(project(":detectImageContent"))
    implementation(project(":domain"))

    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.work.runtime.ktx)
    debugImplementation(libs.ui.tooling)

    implementation(libs.foundation)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.common.ktx)

    //coil
    implementation(libs.coil.kt.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //pagination
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.kotlinx.datetime)
    // Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)
    implementation("androidx.webkit:webkit:1.9.0")
    //Dagger- Hilt
    ksp(libs.hilt.android.compiler.v2511)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation (libs.dagger)

    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    implementation (libs.androidx.core.splashscreen)

}