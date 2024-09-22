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
    implementation("org.ow2.asm:asm:9.2")
    implementation("org.ow2.asm:asm-util:9.2")
    implementation("org.ow2.asm:asm-commons:9.2")
    implementation("com.android.tools:common:30.1.0")

}
kotlin {
    jvmToolchain(17)
}