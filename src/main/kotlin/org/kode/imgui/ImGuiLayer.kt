package org.kode.imgui

import imgui.ImGui
import org.kode.gameobject.Globals
import org.kode.scene.SceneManager

class ImGuiLayer {

    fun imgui() {
        Globals.imgui()
        SceneManager.imgui()

        ImGui.showDemoWindow()
    }

}