package org.kode.debug

import org.lwjgl.glfw.GLFWErrorCallback

class GLFWErrorHandler : GLFWErrorCallback() {
    override fun invoke(error: Int, description: Long) {
        val errorMsg = getDescription(description)
        Log.fatal(errorMsg, "GLFW")
    }
}