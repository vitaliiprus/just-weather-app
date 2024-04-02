import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val props = Properties()
val propertiesFile = File(project.projectDir, "remote.properties")
if (propertiesFile.exists() && propertiesFile.isFile) {
    propertiesFile.inputStream().use { input ->
        props.load(input)
    }
}

android {
    namespace = "prus.justweatherapp.remote"

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        buildConfigField("String", "OPENWEATHER_BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        buildConfigField("String", "OPENWEATHER_API_KEY", props.getProperty("OPENWEATHER_API_KEY"))
        buildConfigField("String", "OPENMETEO_BASE_URL", "\"https://api.open-meteo.com/v1/\"")
    }

    buildFeatures{
        buildConfig = true
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
    implementation(libs.bundles.retrofit2)
    implementation(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}