package org.kode.gameobject.component

import org.joml.Vector2f
import org.kode.data.serializing.HideInInspector
import org.kode.gameobject.Transform
import org.kode.rendering.Sprite
import org.kode.rendering.Texture
import org.kode.util.Color

class SpriteRenderer : Component() {

    @HideInInspector
    var sprite: Sprite = Sprite()
        set(value) {
            if (field == value) return
            field = value
            this.isDirty = true
        }
    var color: Color = Color().apply {
        r = 255
        g = 255
        b = 255
        a = 255
    }
        set(value) {
            if (field == value) return
            field = value
            this.isDirty = true
        }

    @HideInInspector
    var isDirty: Boolean = true
    @HideInInspector
    private lateinit var lastTransform: Transform

    val texture: Texture?
        get() = sprite.texture
    val texCoords: Array<Vector2f>
        get() = sprite.texCoords

    override fun start() {
        this.lastTransform = this.transform.copy()
    }

    override fun update() {
        if (this.transform == this.lastTransform) return

        this.lastTransform = this.transform.copy()
        this.isDirty = true
    }
}