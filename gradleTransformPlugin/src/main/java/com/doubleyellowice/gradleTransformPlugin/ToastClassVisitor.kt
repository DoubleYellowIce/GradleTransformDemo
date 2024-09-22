package com.doubleyellowice.gradleTransformPlugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ToastClassVisitor(nextVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM6, nextVisitor) {

    override fun visitOuterClass(owner: String?, name: String?, descriptor: String?) {
        println("Owner:$owner")
        return super.visitOuterClass(owner, name, descriptor)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }
}