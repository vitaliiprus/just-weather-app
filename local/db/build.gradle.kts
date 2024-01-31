plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "prus.justweatherapp.local.db"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

dependencies {
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
}