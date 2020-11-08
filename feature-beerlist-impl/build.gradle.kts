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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":feature-beerlist-api"))
    implementation(project(":feature-details-api"))
    implementation(project(":core-ui-base"))
    implementation(project(":core-db-api"))
    implementation(project(":core-api"))
    implementation(project(":core-navigation"))
    implementation(Libs.kotlin)
    implementation(Libs.appCompat)
    implementation(Libs.constraintLayout)
    implementation(Libs.material)
    implementation(Libs.fragment)
    implementation(Libs.delegates)
    implementation(Libs.coroutines)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)

}