package com.doubleyellowice.gradleTransformPlugin

import com.android.SdkConstants
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.builder.utils.isValidZipEntryName
import com.android.utils.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import com.google.common.io.Files
import org.gradle.jvm.tasks.Jar
import java.util.zip.ZipEntry

import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class ToastTransform : Transform() {
    override fun getName(): String {
        return this.javaClass.simpleName
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        println("ToastTransform: transforming")
        val inputProvider = transformInvocation?.inputs
        val outputProvider = transformInvocation?.outputProvider
        if (inputProvider != null && outputProvider != null) {
            for (input in inputProvider) {
//                transformJarInputs(input.jarInputs, outputProvider)
                transformClassFiles(input.directoryInputs, outputProvider)
            }

        } else {
            println("inputProvider is null, failed to transform")
        }
    }

    private fun transformJarInputs(
        jarInputs: Collection<JarInput>,
        outputProvider: TransformOutputProvider
    ) {
        for (jarInput in jarInputs) {
            val outputJar = outputProvider.getContentLocation(
                jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
            )
            transformJarInput(jarInput.file, outputJar)
        }
    }

    private fun transformClassFiles(
        dirInputs: Collection<DirectoryInput>, outputProvider: TransformOutputProvider
    ) {
        for (dirInput in dirInputs) {
            val inputDir = dirInput.file
            val outputDir = outputProvider.getContentLocation(
                dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY
            )
            for (inputFile in FileUtils.getAllFiles(inputDir)) {
                println("current file name ${inputFile.name}")
                val outputFile = concatOutputFilePath(outputDir, inputFile)
                if (classFilter(inputFile.name)) {
                    transformClassFile(inputFile, outputFile)
                } else {
                    // Copy.
                    Files.createParentDirs(outputFile)
                    FileUtils.copyFile(inputFile, outputFile)
                }
            }
        }
    }

    private fun concatOutputFilePath(outputDir: File, inputFile: File) =
        File(outputDir, inputFile.name)


    private fun transformJarInput(inputJar: File, outputJar: File) {
        Files.createParentDirs(outputJar)
        FileInputStream(inputJar).use { fis ->
            ZipInputStream(fis).use { zis ->
                FileOutputStream(outputJar).use { fos ->
                    ZipOutputStream(fos).use { zos ->
                        var entry = zis.nextEntry
                        while (entry != null && isValidZipEntryName(entry)) {
                            if (!entry.isDirectory) {
                                zos.putNextEntry(ZipEntry(entry.name))
                                if (classFilter(entry.name)) {
                                    // Apply transform function.
                                    val classReader = ClassReader(zis)
                                    val classWriter =
                                        ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES)
                                    val toastVisitor = ToastClassVisitor(classWriter)
                                    classReader.accept(toastVisitor, ClassReader.EXPAND_FRAMES)
                                    zos.write(classWriter.toByteArray())
                                } else {
                                    // Copy.
                                    zis.copyTo(zos)
                                }

                            }
                            entry = zis.nextEntry
                        }
                    }

                }

            }
        }
    }

    /**
     * Do transform file.
     */
    private fun transformClassFile(
        inputFile: File,
        outputFile: File,
    ) {
        // Create parent directories to hold outputFile file.
        Files.createParentDirs(outputFile)
        FileInputStream(inputFile).use { fis ->
            FileOutputStream(outputFile).use { fos ->
                val classReader = ClassReader(fis)
                val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES)
                val toastVisitor = ToastClassVisitor(classWriter)
                classReader.accept(toastVisitor, ClassReader.EXPAND_FRAMES)
                fos.write(classWriter.toByteArray())
            }
        }
    }


    fun classFilter(className: String) = className.endsWith(SdkConstants.DOT_CLASS)
}