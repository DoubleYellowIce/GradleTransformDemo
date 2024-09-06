package com.doubleyellowice.gradleTransformPlugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class TransformPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.getLogger().lifecycle("TransformPlugin has been applied to project: " + target.getName());
    }
}
