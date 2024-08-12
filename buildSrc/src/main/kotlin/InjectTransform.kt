//package com.doubleyellowice.transformplugin
//
//import com.android.build.api.transform.Context
//import com.android.build.api.transform.QualifiedContent
//import com.android.build.api.transform.Transform
//import com.android.build.api.transform.TransformInput
//import com.android.build.api.transform.TransformOutputProvider
//import com.android.build.gradle.internal.pipeline.TransformManager
//import org.gradle.api.Project
//
//
//class InjectTransform(val project: Project) : Transform() {
//
//
//    override fun getName(): String {
//        return this.javaClass.simpleName
//    }
//
//    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
//        return TransformManager.CONTENT_CLASS
//    }
//
//    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
//        return TransformManager.SCOPE_FULL_PROJECT
//    }
//
//    override fun isIncremental(): Boolean {
//        return false
//    }
//
//    override fun transform(
//        context: Context?,
//        inputs: MutableCollection<TransformInput>?,
//        referencedInputs: MutableCollection<TransformInput>?,
//        outputProvider: TransformOutputProvider?,
//        isIncremental: Boolean
//    ) {
//        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
//        inputs?.forEach { input ->
//            input.directoryInputs
//
//        }
//    }
//}