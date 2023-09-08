package org.kode.gameobject.component

import org.joml.Vector2f
import org.kode.gameobject.GameObject
import org.kode.input.Input
import org.kode.math.*

class MouseControls : Component() {

    var holdingObject: GameObject? = null

    fun pickupObject(gameObject: GameObject) {
        this.holdingObject = gameObject
        this.scene.addGameObject(gameObject)
    }

    fun place() {
        this.holdingObject = null
    }

    override fun update() {
        if (this.holdingObject == null) return
        this.holdingObject!!.transform.position = this.scene.camera.screenToWorldPosition(Input.mousePosition)

        if (!Input.getMouseButtonDown(0)) return
        this.place()
    }

}