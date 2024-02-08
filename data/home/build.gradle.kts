plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "prus.justweatherapp.data.home"

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain:home"))
    implementation(project(":core"))

    implementation(libs.bundles.kotlinx.coroutines)
    implementation(libs.timber)

    implementation(libs.javax.inject)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
}