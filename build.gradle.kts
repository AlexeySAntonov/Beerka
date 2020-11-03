buildscript {
  repositories {
    jcenter()
    google()
  }
  dependencies {
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath(BuildPlugins.androidTools)
    classpath(BuildPlugins.googleServices)
  }
}

allprojects {
  repositories {
    jcenter()
    google()
  }
}