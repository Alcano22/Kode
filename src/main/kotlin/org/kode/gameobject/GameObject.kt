package org.kode.gameobject

import com.google.gson.annotations.JsonAdapter
import imgui.ImGui
import imgui.type.ImInt
import org.kode.data.serializing.GameObjectJsonAdapter
import org.kode.debug.Log
import org.kode.gameobject.component.Component
import org.kode.scene.SceneManager

@JsonAdapter(GameObjectJsonAdapter::class)
class GameObject(var transform: Transform = Transform(), val zIndex: Int = 0, val name: String = "GameObject") {

    var tag: String = "Default"

    companion object {
        fun findGameObjects(name: String): Array<GameObject> {
            val currentScene = SceneManager.currentScene
            if (currentScene == null) {
                Log.error("No scene has been loaded!")
                return emptyArray()
            }

            val gameObjects = currentScene.gameObjects
            return gameObjects.filter { it.name == name }.toTypedArray()
        }

        fun findGameObject(name: String): GameObject? {
            return this.findGameObjects(name).firstOrNull()
        }
    }

    val components: MutableList<Component> = ArrayList()

    fun start() {
        for (i in this.components.indices) {
            this.components[i].start()
        }
    }

    fun update() {
        for (i in this.components.indices) {
            this.components[i].update()
        }
    }

    fun imgui() {
        val tags = Globals.tags
        val imInt = ImInt(tags.indexOf(this.tag))
        if (ImGui.combo("Tag", imInt, tags.toTypedArray())) {
            this.tag = tags[imInt.get()]
        }

        transform.imgui()

        for (i in this.components.indices) {
            this.components[i].imgui()
        }
    }

    inline fun <reified T : Component> getComponent(): T? {
        val filteredComponents = this.components.filterIsInstance<T>()
        return filteredComponents.firstOrNull()
    }

    inline fun <reified T : Component> removeComponent() {
        for (i in this.components.indices) {
            val component = this.components[i]
            if (component !is T) continue

            this.components.remove(component)
        }
    }

    fun <T : Component> addComponent(component: T) {
        this.components.add(component)
        component.gameObject = this
    }

}