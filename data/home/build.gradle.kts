plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "prus.justweatherapp.data.home"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

dependencies {
    implementation(project(":domain:home"))
    implementation(project(":core"))

    implementation(libs.androidx.core)
    implementation(libs.bundles.kotlinx.coroutines)
    implementation(libs.timber)
    implementation(libs.bundles.retrofit2)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}