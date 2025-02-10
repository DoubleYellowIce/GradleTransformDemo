# Gradle Transform Plugin Demo

---

## 中文版

### 简介
本项目演示了如何借助 Gradle Transform 技术对字节码进行插桩。

### 使用步骤

1. **临时禁用插件**

   在 `app` 模块的 `build.gradle` 文件中，先将 `TransformPlugin` 插件的应用配置注释掉：

   ```kotlin
   plugins {
       // id("TransformPlugin")
   }
   ```

2. **发布插件**

   执行以下命令来构建并发布 Gradle 插件 demo：

   ```bash
   ./gradlew publishAllPublicationsToMavenRepository
   ```

3. **启用插件**

   发布完成后，取消注释 `TransformPlugin` 配置：

   ```kotlin
   plugins {
       id("TransformPlugin")
   }
   ```

4. **运行应用**

   直接运行 `app` 模块，启动后你将看到带有 “Injected By ASM” 提示的 Toast 消息，表明字节码插桩已成功应用。

---

## English Version

### Overview
This project demonstrates a Gradle plugin demo that leverages the Gradle Transform API for bytecode instrumentation. 

### Usage Steps

1. **Temporarily Disable the Plugin**

   In the `app` module's `build.gradle` file, comment out the application of the `TransformPlugin`:

   ```kotlin
   plugins {
       // id("TransformPlugin")
   }
   ```

2. **Publish the Plugin**

   Execute the following command to build and publish the Gradle plugin demo:

   ```bash
   ./gradlew publishAllPublicationsToMavenRepository
   ```

3. **Enable the Plugin**

   After the plugin has been published, uncomment the `TransformPlugin` configuration:

   ```kotlin
   plugins {
       id("TransformPlugin")
   }
   ```

4. **Run the App**

   Run the `app` module. Once launched, you should see a Toast message stating “Injected By ASM”, indicating that the bytecode instrumentation was successfully applied.

