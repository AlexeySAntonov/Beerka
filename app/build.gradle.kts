plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId("com.aleksejantonov.beerka")
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core-di"))
    implementation(project(":core-ui-base"))
    implementation(project(":core-db-impl"))
    implementation(project(":core-navigation"))
    implementation(project(":feature-beerlist-impl"))
    implementation(project(":feature-favorites-impl"))
    implementation(project(":feature-details-impl"))
    implementation(Libs.kotlin)
    implementation(Libs.ktx)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.fragment)
    implementation(Libs.lifecycleViewModelKtx)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
    implementation(Libs.leakCanary)
}