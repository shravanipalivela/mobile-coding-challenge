plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")  // Enable kapt for Kotlin annotation processing
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core Dependencies (Android and Kotlin essentials)
    implementation(libs.androidx.core.ktx)                    // Android KTX extensions
    implementation(libs.androidx.lifecycle.runtime.ktx)        // Lifecycle runtime for KTX
    implementation(libs.androidx.activity.compose)             // Compose integration with activity
    implementation(platform(libs.androidx.compose.bom))        // BOM for Compose
    implementation(libs.androidx.ui)                            // UI framework for Compose
    implementation(libs.androidx.ui.graphics)                   // UI graphics for Compose
    implementation(libs.androidx.ui.tooling.preview)            // UI tooling preview for Compose
    implementation(libs.coil.compose)
    implementation(libs.logging.interceptor)

    // Material 3 components for Compose
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // Networking and Data
    implementation(libs.retrofit.core)                         // Retrofit for API calls
    implementation(libs.retrofit.gson)                         // Gson converter for Retrofit
    implementation(libs.androidx.navigation.compose)           // Navigation for Compose

    // Hilt for Dependency Injection
    implementation(libs.android.hilt.dagger)                    // Hilt Dagger dependencies
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.runtime.livedata)     // Hilt Navigation for Compose
    kapt(libs.hilt.compiler)                                   // Hilt compiler for annotation processing
    kaptAndroidTest(libs.hilt.android.compiler)                // Hilt compiler for androidTest

    // Testing
    testImplementation(libs.junit)                             // JUnit for unit testing
    testImplementation(libs.androidx.core.testing)            // Core testing utilities for Android
    testImplementation(libs.mockk)                             // Mocking framework for unit tests
    testImplementation(libs.kotlinx.coroutines.test)          // Coroutines test support
    testImplementation(libs.mockwebserver)
    testImplementation(libs.robolectric)

    // Android Tests
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM for Compose in Android tests
    androidTestImplementation(libs.androidx.ui.test.junit4)   // UI tests for Compose
    androidTestImplementation(libs.hilt.android.testing)      // Hilt testing dependencies
    androidTestImplementation(libs.androidx.espresso.core)   // Espresso for UI testing
    androidTestImplementation(libs.androidx.junit)           // JUnit for Android tests // Core testing utilities for Android tests
    androidTestImplementation(libs.androidx.material3)
    androidTestImplementation(libs.androidx.hilt.navigation.compose)
    androidTestImplementation(libs.mockk)
    // Debug Dependencies
    debugImplementation(libs.androidx.ui.tooling)            // UI tooling for debugging Compose
    debugImplementation(libs.androidx.ui.test.manifest)       // Test manifest for debugging Compose

}