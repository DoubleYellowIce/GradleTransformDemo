package com.doubleyellowice.gradleTransformPlugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.logger.lifecycle("TransformPlugin has been applied to project: " + target.name)
    }
}
