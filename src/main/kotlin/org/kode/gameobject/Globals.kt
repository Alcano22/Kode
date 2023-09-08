package org.kode.gameobject

import imgui.ImGui
import org.kode.imgui.ImGuiTypes
import org.kode.util.hasDuplicates

object Globals {

    var tags: MutableList<String> = mutableListOf("Default")
        private set
    var layers: MutableList<String> = mutableListOf("Default")
        private set

    fun imgui() {
        ImGui.begin("Globals")

        ImGui.text("Tags")
        this.tags = ImGuiTypes.list("Tags", this.tags, "", intArrayOf(0))
        if (this.tags.hasDuplicates()) {
            ImGui.textColored(196, 150, 33, 255, "Warning!\nDuplicate tags can lead to bugs!")
        }

        ImGui.separator()

        ImGui.text("Layers")
        this.layers = ImGuiTypes.list("Layers", this.layers, "", intArrayOf(0))
        if (this.layers.hasDuplicates()) {
            ImGui.textColored(196, 150, 33, 255, "Warning!\nDuplicate layers can lead to bugs!")
        }

        ImGui.end()
    }

}