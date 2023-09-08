package org.kode.util

import org.lwjgl.glfw.GLFW.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Time {
    val time: Float
        get() = glfwGetTime().toFloat()
    val timeSeconds: Float
        get() = time / 1000000000.0f
    var deltaTime: Float = 0.0f
    val timestamp: String
        get() = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now())
}
