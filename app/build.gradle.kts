plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

android {
    signingConfigs {
        create("release") {
            keyAlias = System.getenv()["BEERKA_KEY_ALIAS"] ?: project.property("BEERKA_KEY_ALIAS") as String
            keyPassword = System.getenv()["BEERKA_KEY_PASSWORD"] ?: project.property("BEERKA_KEY_PASSWORD") as String
            storeFile = file(System.getenv()["BEERKA_KEYSTORE_PATH"] ?: project.property("BEERKA_KEYSTORE_PATH") as String)
            storePassword = System.getenv()["BEERKA_KEYSTORE_PASSWORD"] ?: project.property("BEERKA_KEYSTORE_PASSWORD") as String
        }
    }

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
            isDebuggable = false
            if (System.getenv()["CI"] == null) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":core-di"))
    implementation(project(":core-api"))
    implementation(project(":core-ui-base"))
    implementation(project(":core-db-impl"))
    implementation(project(":core-navigation"))
    implementation(project(":feature-beerlist-impl"))
    implementation(project(":feature-favorites-impl"))
    implementation(project(":feature-details-impl"))
    implementation(project(":feature-filter-impl"))
    implementation(project(":core-mediator-impl"))

    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)

    debugImplementation(Libs.leakCanary)
}