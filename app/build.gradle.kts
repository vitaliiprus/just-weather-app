plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "prus.justweatherapp.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "prus.justweatherapp"

        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        flavorDimensions += "default"
    }

    productFlavors {
        create("dev") {
            versionNameSuffix = "DEV"
            applicationIdSuffix = ".dev"

            resValue("string", "app_name", rootProject.name + " DEV")
        }

        create("qa") {
            versionNameSuffix = "TEST"
            applicationIdSuffix = ".test"

            resValue("string", "app_name", rootProject.name + " TEST")
        }

        create("prod") {
            resValue("string", "app_name", rootProject.name)
        }

        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
                isShrinkResources = false
                isDebuggable = true
            }
            getByName("release") {
                isShrinkResources = false
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
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
}

dependencies {
    implementation(project(":feature:home"))

    implementation(project(":domain:locations"))
    implementation(project(":data:locations"))
    implementation(project(":local:db"))

    implementation(project(":theme"))
    implementation(project(":core"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}