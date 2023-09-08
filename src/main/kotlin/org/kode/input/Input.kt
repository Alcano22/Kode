package org.kode.input

import imgui.ImGui
import org.joml.Vector2f
import org.kode.math.*
import org.lwjgl.glfw.GLFW.*

// Callback TAs
typealias MousePosCallback = (Long, Double, Double) -> Unit
typealias MouseScrollCallback = (Long, Double, Double) -> Unit
typealias MouseButtonCallback = (Long, Int, Int, Int) -> Unit
typealias KeyCallback = (Long, Int, Int, Int, Int) -> Unit

const val Horizontal: String = "Horizontal"
const val Vertical: String = "Vertical"
const val MouseX: String = "Mouse X"
const val MouseY: String = "Mouse Y"

object Input {

    private val keyboard: KeyboardListener = KeyboardListener()
    private val mouse: MouseListener = MouseListener()

    val keyCallback: KeyCallback get() = this.keyboard.keyCallback
    val mousePosCallback: MousePosCallback get() = this.mouse.mousePosCallback
    val mouseScrollCallback: MouseScrollCallback get() = this.mouse.mouseScrollCallback
    val mouseButtonCallback: MouseButtonCallback get() = this.mouse.mouseButtonCallback

    val mousePosition: Vector2f get() = Vector2f(this.mouse.posX, this.mouse.posY)
    val lastMousePosition: Vector2f get() = Vector2f(this.mouse.lastX, this.mouse.lastY)
    val mouseDelta: Vector2f get() = lastMousePosition - mousePosition
    val scrollDelta: Vector2f get() = Vector2f(this.mouse.scrollX, this.mouse.scrollY)
    val isDragging: Boolean get() = this.mouse.isDragging

    fun endFrame() {
        this.keyboard.endFrame()
        this.mouse.endFrame()
    }

    fun getKey(key: Int): Boolean = this.keyboard.getKey(key)
    fun getKeyDown(key: Int): Boolean = this.keyboard.getKeyDown(key)
    fun getKeyUp(key: Int): Boolean = this.keyboard.getKeyUp(key)

    fun getMouseButton(button: Int): Boolean = this.mouse.getButton(button)
    fun getMouseButtonDown(button: Int): Boolean = this.mouse.getButtonDown(button)
    fun getMouseButtonUp(button: Int): Boolean = this.mouse.getButtonUp(button)

    fun getAxis(axis: String): Float {
        return when (axis) {
            Horizontal -> if (getKey(GLFW_KEY_A)) -1.0f else if (getKey(GLFW_KEY_D)) 1.0f else 0.0f
            Vertical -> if (getKey(GLFW_KEY_S)) -1.0f else if (getKey(GLFW_KEY_W)) 1.0f else 0.0f
            MouseX -> if (mouseDelta.x < 0.0f) -1.0f else if (mouseDelta.x > 0.0f) 1.0f else 0.0f
            MouseY -> if (mouseDelta.y < 0.0f) -1.0f else if (mouseDelta.y > 0.0f) 1.0f else 0.0f
            else -> error("Unknown input")
        }
    }

    fun getKeyAxes(): Vector2f = Vector2f(this.getAxis(Horizontal), this.getAxis(Vertical))
    fun getMouseAxes(): Vector2f = Vector2f(this.getAxis(MouseX), this.getAxis(MouseY))

}
