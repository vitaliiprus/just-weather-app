plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "prus.justweatherapp.data.locations"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

dependencies {
    implementation(project(":domain:locations"))
    implementation(project(":local:db"))
    implementation(project(":core"))

    implementation(libs.bundles.kotlinx.coroutines)
    implementation(libs.timber)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}