package org.kode.scene

import imgui.ImGui
import org.kode.core.Engine
import org.kode.debug.Log
import org.kode.gameobject.GameObject
import org.kode.rendering.Camera
import org.kode.rendering.SceneRenderer
import java.io.File

abstract class Scene {

    val camera: Camera = Camera()
    val gameObjects: MutableList<GameObject> = ArrayList()

    protected var isLevelLoaded: Boolean = false
    protected var activeGameObject: GameObject? = null

    private val renderer: SceneRenderer = SceneRenderer()
    private var isRunning: Boolean = false

    open fun init() {}

    open fun start() {
        this.gameObjects.forEach {
            it.start()
            this.renderer.add(it)
        }
        this.isRunning = true
    }

    open fun update() {
        this.gameObjects.forEach { it.update() }

        this.renderer.render()
    }

    open fun imgui() {}

    fun imguiScene() {
        if (this.activeGameObject != null) {
            ImGui.begin("Inspector")
            this.activeGameObject!!.imgui()
            ImGui.end()
        }

        this.imgui()
    }

    fun save() {
        val file = File("level.txt")
        if (!file.exists()) {
            file.createNewFile()
        }

        file.writeText(Engine.gson.toJson(this.gameObjects))
    }

    fun load() {
        val file = File("level.txt")
        if (!file.exists()) {
            file.createNewFile()
        }

        val fileContents = file.readText()
        if (fileContents.isEmpty()) return

        val gameObjects = Engine.gson.fromJson(fileContents, Array<GameObject>::class.java)
        gameObjects.forEach { this.addGameObject(it) }

        this.isLevelLoaded = true
    }

    fun addGameObject(gameObject: GameObject) {
        this.gameObjects.add(gameObject)

        if (!this.isRunning) return

        gameObject.start()
        this.renderer.add(gameObject)
    }

}