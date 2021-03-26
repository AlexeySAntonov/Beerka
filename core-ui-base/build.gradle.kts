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
}

dependencies {
    api(project(":core-resources"))
    api(project(":core-ui-model"))
    api(project(":core-di"))
    api(project(":module-injector"))
    api(Libs.kotlin)
    api(Libs.appCompat)
    api(Libs.fragment)
    api(Libs.material)
    api(Libs.constraintLayout)
    api(Libs.delegates)
    api(Libs.ktx)
    api(Libs.lifecycleExtensions)
    api(Libs.lifecycleViewModelKtx)
    api(Libs.timber)
    api(Libs.glide)
    kapt(Libs.glideCompiler)
    implementation(Libs.coroutines)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
}