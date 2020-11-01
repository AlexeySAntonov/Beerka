buildscript {
    val kotlin_version by extra("1.4.10")
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.androidTools)
        classpath(BuildPlugins.googleServices)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}