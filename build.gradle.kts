// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven(url = "./repo")
    }
    dependencies {
        classpath("com.doubleyellowice:gradleTransformPlugin:1.0.0")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}