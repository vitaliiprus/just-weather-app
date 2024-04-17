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
                isMinifyEnabled = true
                isShrinkResources = true
                isDebuggable = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        buildFeatures {
            buildConfig = true
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
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":feature:locations"))
    implementation(project(":feature:weather"))
    implementation(project(":feature:settings"))

    implementation(project(":domain:locations"))
    implementation(project(":domain:weather"))
    implementation(project(":domain:settings"))

    implementation(project(":data:locations"))
    implementation(project(":data:weather"))
    implementation(project(":data:settings"))

    implementation(project(":local:db"))
    implementation(project(":remote"))

    implementation(project(":theme"))
    implementation(project(":core:common"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.timber)

    debugImplementation(libs.leakcanary)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}