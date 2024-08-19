plugins {
    `kotlin-dsl`
}
gradlePlugin {
    plugins {
        create("TransformPlugin") {
            id = "TransformPlugin"
            implementationClass = "TransformPlugin"
        }
    }
}
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies{
    implementation(gradleApi())
    //TODO fix error 'The request for this plugin could not be satisfied because the plugin is already on the classpath with an unknown version, so compatibility cannot be checked.'
    //implementation("com.android.tools.build:gradle:7.4.2") //fix the bug above to uncomment this dependency declaration
    implementation("org.javassist:javassist:3.30.2-GA")
}
