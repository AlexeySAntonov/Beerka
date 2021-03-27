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
        consumerProguardFiles("consumer-rules.pro")
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
    api(project(":core-model"))
    api(Libs.retrofit2)
    api(Libs.retrofit2ConverterGson)
    api(Libs.gson)
    api(Libs.stetho)

    implementation(project(":core-controller"))
    implementation(Libs.kotlin)
    implementation(Libs.coroutines)
    implementation(Libs.okhttp3Logging)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
    implementation(Libs.stethoOkHttp)
}