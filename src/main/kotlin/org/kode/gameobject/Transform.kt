package org.kode.gameobject

import imgui.ImGui
import imgui.type.ImBoolean
import org.joml.Vector2f
import org.kode.imgui.ImGuiTypes

data class Transform(var position: Vector2f = Vector2f(0.0f, 0.0f), var rotation: Float = 0.0f, var scale: Vector2f = Vector2f(1.0f, 1.0f)) {

    fun imgui() {
        if (!ImGui.collapsingHeader("Transform")) return

        this.position = ImGuiTypes.vec2f("Position", this.position)
        this.rotation = ImGuiTypes.float("Rotation", this.rotation)
        this.scale = ImGuiTypes.vec2f("Scale", this.scale)
    }

}
