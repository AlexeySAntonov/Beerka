object Versions {
    const val buildTools = "30.0.2"
    const val compileSdk = 30
    const val targetSdk = 30
    const val minSdk = 21

    const val kotlin = "1.4.10"
    const val retrofit = "2.9.0"
    const val stetho = "1.5.1"
    const val glide = "4.11.0"
    const val dagger = "2.28"
    const val lifecycle = "2.2.0"
    const val room = "2.2.5"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:1.3.0"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.8"

    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.3"

    const val fragment = "androidx.fragment:fragment-ktx:1.2.4"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val material = "com.google.android.material:material:1.2.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val delegates = "com.hannesdorfmann:adapterdelegates4:4.3.0"

    //Lifecycle
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    //Network
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp3Logging = "com.squareup.okhttp3:logging-interceptor:4.7.2"
    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    const val gson = "com.google.code.gson:gson:2.8.6"

    //Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideTransformations = "jp.wasabeef:glide-transformations:4.1.0"

    // Database
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}

object BuildPlugins {
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val androidTools = "com.android.tools.build:gradle:4.1.0"
    const val googleServices = "com.google.gms:google-services:4.3.4"
}