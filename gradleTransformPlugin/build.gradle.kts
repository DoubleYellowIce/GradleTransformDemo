plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm")
}
gradlePlugin {
    plugins {
        create("TransformPlugin") {
            id = "TransformPlugin"
            implementationClass = "com.doubleyellowice.gradleTransformPlugin.TransformPlugin"
        }
    }
}
//配置发布本地 maven
publishing {
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("../../repo"))
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.doubleyellowice"
            artifactId = "gradleTransformPlugin"
            version = "1.0.0"
            from(components["java"])
        }
    }
}
dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:7.2.0")
    implementation("org.javassist:javassist:3.30.2-GA")
    implementation(kotlin("stdlib-jdk8"))
}
kotlin {
    jvmToolchain(17)
}