plugins {
    `java-gradle-plugin`
    `maven-publish`
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
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies{
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:3.3.3")
    implementation("org.javassist:javassist:3.30.2-GA")
}
