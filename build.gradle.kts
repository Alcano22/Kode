plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.alcano"
version = "1.0.0"

val kotlinLoggingVersion = "5.1.0"
val gsonVersion = "2.10.1"
val jomlVersion = "1.10.5"
val imguiVersion = "1.86.10"
val lwjglVersion = "3.3.2"
val lwjglNatives = "natives-windows"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin Reflect
    implementation(kotlin("reflect"))

    // Gson
    implementation("com.google.code.gson:gson:$gsonVersion")

    // JOML
    implementation("org.joml", "joml", jomlVersion)

    // ImGui
    implementation("io.github.spair:imgui-java-app:$imguiVersion")
    runtimeOnly("io.github.spair:imgui-java-natives-windows:$imguiVersion")

    // LWJGL
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-assimp")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-nfd")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("org.kode.core.EngineKt")
}