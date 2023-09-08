package org.kode.input

import imgui.ImGui
import org.lwjgl.glfw.GLFW.*

class KeyboardListener {

    companion object {
        const val NUM_KEYS: Int = 350
    }

    private val pressedKeys: BooleanArray = BooleanArray(NUM_KEYS)
    private val currentKeys: BooleanArray = BooleanArray(NUM_KEYS)
    private val downKeys: BooleanArray = BooleanArray(NUM_KEYS)
    private val upKeys: BooleanArray = BooleanArray(NUM_KEYS)

    val keyCallback: KeyCallback
        get() = { _, key, _, action, _ ->
            if (action == GLFW_PRESS) {
                this.pressedKeys[key] = true
            } else if (action == GLFW_RELEASE) {
                this.pressedKeys[key] = false
            }
        }

    fun endFrame() {
        this.upKeys.fill(false)
        for (i in 0..<NUM_KEYS) {
            this.upKeys[i] = !this.pressedKeys[i] && this.currentKeys[i]
        }

        this.downKeys.fill(false)
        for (i in 0..<NUM_KEYS) {
            this.downKeys[i] = this.pressedKeys[i] && !this.currentKeys[i]
        }

        this.currentKeys.fill(false)
        for (i in 0..<NUM_KEYS) {
            this.currentKeys[i] = this.pressedKeys[i]
        }
    }

    fun getKey(key: Int): Boolean {
        if (key >= NUM_KEYS) return false

        return this.currentKeys[key]
    }

    fun getKeyDown(key: Int): Boolean {
        if (key >= NUM_KEYS) return false

        return this.downKeys[key]
    }

    fun getKeyUp(key: Int): Boolean {
        if (key >= NUM_KEYS) return false

        return this.upKeys[key]
    }
}