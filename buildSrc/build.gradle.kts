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
//    implementation("com.android.tools.build:gradle:3.3.3")
    implementation("org.javassist:javassist:3.30.2-GA")
}
