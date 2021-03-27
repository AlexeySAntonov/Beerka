plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        getByName("release") {
            consumerProguardFile("consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":feature-beerlist-api"))
    implementation(project(":feature-favorites-api"))
    implementation(project(":feature-details-api"))
    implementation(project(":feature-filter-api"))
    implementation(Libs.kotlin)
    implementation(Libs.appCompat)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
}