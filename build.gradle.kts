// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle)
//        classpath(libs.google.services)
//        classpath(libs.firebase.crashlytics.gradle)

        classpath(libs.kotlin.gradle)
        classpath(kotlin("serialization", version = libs.versions.kotlin.get()))
        classpath(libs.dagger.hilt.agp)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}