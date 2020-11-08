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
    api(project(":core-di"))
    implementation(Libs.kotlin)
    implementation(Libs.appCompat)
    implementation(Libs.fragment)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.delegates)
    implementation(Libs.ktx)
    implementation(Libs.kotlin)
    implementation(Libs.lifecycleExtensions)
    implementation(Libs.timber)
    implementation(Libs.glide)
    implementation(Libs.coroutines)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
}