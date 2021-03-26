import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    jcenter()
    google()
  }
  dependencies {
    classpath(BuildPlugins.kotlinGradlePlugin)
    classpath(BuildPlugins.AGP)
    classpath(BuildPlugins.googleServices)
  }
}

allprojects {
  repositories {
    jcenter()
    google()
  }

  tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "1.8"
      useIR = true
    }
  }

}