package org.kode.core

import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import org.kode.debug.GLFWErrorHandler
import org.kode.debug.Log
import org.kode.imgui.ImGuiLayer
import org.kode.input.Input
import org.kode.scene.SceneManager
import org.kode.util.Time
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL
import kotlin.system.exitProcess

object Window {

    private const val GLSL_VERSION = "#version 330 core"

    private val imGuiGlfw: ImGuiImplGlfw = ImGuiImplGlfw()
    private val imGuiGl3: ImGuiImplGl3 = ImGuiImplGl3()
    private val imGuiLayer: ImGuiLayer = ImGuiLayer()

    var width: Int = 1920
        private set
    var height: Int = 1080
        private set
    private var title: String = "Kode Game"
    private var window: Long = 0

    fun run() {
        Log.info("Hello LWJGL " + Version.getVersion() + "!")

        this.initWindow()
        this.initImGui()

        this.loop()
        this.cleanup()
    }

    private fun initImGui() {
        ImGui.createContext()

        val io = ImGui.getIO()
        io.fonts.addFontFromFileTTF("assets/fonts/segoe_ui.ttf", 18.0f)

        this.imGuiGlfw.init(this.window, true)
        this.imGuiGl3.init(GLSL_VERSION)
    }

    private fun initWindow() {
        glfwSetErrorCallback(GLFWErrorHandler())

        if (!glfwInit()) {
            Log.fatal("Failed to initialize GLFW")
            exitProcess(1)
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE)

        this.window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL)
        if (this.window == NULL) {
            Log.fatal("Failed to create the GLFW window")
            exitProcess(1)
        }

        glfwSetCursorPosCallback(this.window, Input.mousePosCallback)
        glfwSetScrollCallback(this.window, Input.mouseScrollCallback)
        glfwSetMouseButtonCallback(this.window, Input.mouseButtonCallback)
        glfwSetKeyCallback(this.window, Input.keyCallback)

        glfwMakeContextCurrent(this.window)
        glfwSwapInterval(1)

        glfwShowWindow(this.window)

        GL.createCapabilities()

        glEnable(GL_BLEND)
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA)
    }

    private fun loop() {
        var beginTime = Time.time
        var endTime: Float

        SceneManager.loadScene(0)
        while (!glfwWindowShouldClose(this.window)) {
            Input.endFrame()

            glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
            glClear(GL_COLOR_BUFFER_BIT)

            SceneManager.update()

            this.imGuiGlfw.newFrame()
            ImGui.newFrame()

            this.imGuiLayer.imgui()

            ImGui.render()
            this.imGuiGl3.renderDrawData(ImGui.getDrawData())

            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                val backupWindow = glfwGetCurrentContext()
                ImGui.updatePlatformWindows()
                ImGui.renderPlatformWindowsDefault()
                glfwMakeContextCurrent(backupWindow)
            }

            glfwSwapBuffers(this.window)

            glfwPollEvents()

            endTime = Time.time
            Time.deltaTime = endTime - beginTime
            beginTime = endTime
        }

        SceneManager.save()
    }

    private fun cleanup() {
        this.imGuiGl3.dispose()
        this.imGuiGlfw.dispose()
        ImGui.destroyContext()

        glfwFreeCallbacks(this.window)
        glfwDestroyWindow(this.window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

}