package com.doubleyellowice.gradleTransformPlugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.logger.lifecycle("TransformPlugin has been applied to project: " + target.name)
        // 1、获取 Android 扩展
        val androidExtension = target.extensions.getByType(AppExtension::class.java)
        // 2、注册 Transform
        androidExtension.registerTransform(ToastTransform())
    }
}
