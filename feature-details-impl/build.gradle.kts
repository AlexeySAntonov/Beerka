plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":feature-details-api"))
    implementation(project(":core-ui-base"))
    implementation(project(":core-db-api"))
    implementation(Libs.kotlin)
    implementation(Libs.appCompat)
    implementation(Libs.constraintLayout)
    implementation(Libs.material)
    implementation(Libs.fragment)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)

}