plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "prus.justweatherapp.feature.locations"

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeKotlinCompiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":feature:weather"))
    implementation(project(":domain:locations"))
    implementation(project(":domain:weather"))
    implementation(project(":theme"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.core)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.bundles.androidx.paging)

    debugImplementation(libs.androidx.compose.ui.tooling)


    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.espresso)
}