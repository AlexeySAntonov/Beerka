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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core-di"))
    implementation(project(":core-api"))
    implementation(project(":core-ui-base"))
    implementation(project(":core-resources"))
    implementation(project(":core-db-impl"))
    implementation(project(":core-navigation"))
    implementation(project(":feature-beerlist-impl"))
    implementation(project(":feature-favorites-impl"))
    implementation(project(":feature-details-impl"))
    implementation(project(":feature-details-next-impl"))

    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
    implementation(Libs.leakCanary)
}