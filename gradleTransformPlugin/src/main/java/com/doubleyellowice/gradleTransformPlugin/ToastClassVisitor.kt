package com.doubleyellowice.gradleTransformPlugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

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
        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if (name == "onCreate") {
            object : AdviceAdapter(Opcodes.ASM6, visitor, access, name, descriptor) {
                override fun onMethodExit(opcode: Int) {
                    //TODO fix bug
                    super.onMethodExit(opcode)

                    // Load 'this' onto the stack (which is the context in an Activity)
                    mv.visitVarInsn(Opcodes.ALOAD, 0)
                    // Load the message onto the stack
                    mv.visitLdcInsn("Toast inserted by ASM!")
                    // Load the duration onto the stack
                    mv.visitFieldInsn(Opcodes.GETSTATIC, "android/widget/Toast", "LENGTH_SHORT", "I")
                    // Call Toast.makeText(Context, CharSequence, int)
                    mv.visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            "android/widget/Toast",
                            "makeText",
                            "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;",
                            false
                    )
                    // Call Toast.show()
                    mv.visitMethodInsn(
                            Opcodes.INVOKEVIRTUAL,
                            "android/widget/Toast",
                            "show",
                            "()V",
                            false
                    )
                }
            }
        } else {
            visitor
        }
    }
}