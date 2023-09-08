package org.kode.input

import org.lwjgl.glfw.GLFW.*

class MouseListener {

    companion object {
        const val NUM_BUTTONS: Int = 5
    }

    var posX: Float = 0.0f
        private set
    var posY: Float = 0.0f
        private set
    var lastX: Float = 0.0f
        private set
    var lastY: Float = 0.0f
        private set
    var scrollX: Float = 0.0f
        private set
    var scrollY: Float = 0.0f
        private set
    var isDragging = false
        private set

    private val pressedButtons: BooleanArray = BooleanArray(NUM_BUTTONS)
    private val currentButtons: BooleanArray = BooleanArray(NUM_BUTTONS)
    private val upButtons: BooleanArray = BooleanArray(NUM_BUTTONS)
    private val downButtons: BooleanArray = BooleanArray(NUM_BUTTONS)

    val mousePosCallback: MousePosCallback
        get() = { _, posX, posY ->
            this.lastX = this.posX
            this.lastY = this.posY
            this.posX = posX.toFloat()
            this.posY = posY.toFloat()
            this.isDragging = this.pressedButtons[0] || this.pressedButtons[1] || this.pressedButtons[2]
        }

    val mouseScrollCallback: MouseScrollCallback
        get() = { _, offsetX, offsetY ->
            this.scrollX = offsetX.toFloat()
            this.scrollY = offsetY.toFloat()
        }

    val mouseButtonCallback: MouseButtonCallback
        get() = { _, button, action, _ ->
            if (button < this.pressedButtons.size) {
                if (action == GLFW_PRESS) {
                    this.pressedButtons[button] = true
                } else {
                    this.pressedButtons[button] = false
                    this.isDragging = false
                }
            }
        }

    fun endFrame() {
        this.upButtons.fill(false)
        for (i in 0..<NUM_BUTTONS) {
            this.upButtons[i] = !this.pressedButtons[i] && this.currentButtons[i]
        }

        this.downButtons.fill(false)
        for (i in 0..<NUM_BUTTONS) {
            this.downButtons[i] = this.pressedButtons[i] && !this.currentButtons[i]
        }

        this.currentButtons.fill(false)
        for (i in 0..<NUM_BUTTONS) {
            this.currentButtons[i] = this.pressedButtons[i]
        }
        
        this.scrollX = 0.0f
        this.scrollY = 0.0f
        this.lastX = this.posX
        this.lastY = this.posY
    }

    fun getButton(button: Int): Boolean {
        if (button >= NUM_BUTTONS) return false

        return this.currentButtons[button]
    }

    fun getButtonDown(button: Int): Boolean {
        if (button >= NUM_BUTTONS) return false

        return this.downButtons[button]
    }

    fun getButtonUp(button: Int): Boolean {
        if (button >= NUM_BUTTONS) return false

        return this.upButtons[button]
    }

}