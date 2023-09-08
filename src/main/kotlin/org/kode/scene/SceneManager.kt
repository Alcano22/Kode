package org.kode.scene

import kotlin.reflect.KClass
import org.kode.util.*

object SceneManager {

    private val SCENES: List<KClass<out Scene>> = listOf(
        LevelEditorScene::class,
        LevelScene::class
    )

    var currentScene: Scene? = LevelEditorScene()
        private set

    fun loadScene(index: Int) {
        val newScene = SCENES[index].constructors[0].call()
        this.currentScene = newScene
        newScene.load()
        newScene.init()
        newScene.start()
    }

    fun update() {
        this.currentScene!!.update()
    }

    fun imgui() {
        this.currentScene!!.imguiScene()
    }

    fun save() {
        this.currentScene!!.save()
    }

    fun load() {
        this.currentScene!!.load()
    }
}