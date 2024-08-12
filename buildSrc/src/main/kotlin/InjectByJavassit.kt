import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

val classPool = ClassPool.getDefault()

fun injectToast(path: String, project: Project) {

    classPool.appendClassPath(path)

    // 查找MainActivity类
    val ctClass: CtClass = classPool.get("com.doubleyellowice.gradletransformdemo.MainActivity")

    // 查找onCreate方法
    val ctMethod: CtMethod = ctClass.getDeclaredMethod("onCreate")

    // 在onCreate方法的开头插入Toast代码
    ctMethod.insertBefore("""
        android.widget.Toast.makeText(this, "Hello from Javassist!", android.widget.Toast.LENGTH_SHORT).show();
    """)

    // 将修改后的类写回到文件
    ctClass.writeFile(path)

    // 释放CtClass以避免内存泄漏
    ctClass.detach()

}