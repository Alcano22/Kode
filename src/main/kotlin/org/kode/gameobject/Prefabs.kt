package org.kode.gameobject

import org.joml.Vector2f
import org.kode.gameobject.component.SpriteRenderer
import org.kode.rendering.Sprite

object Prefabs {

    fun generateSpriteObject(sprite: Sprite, width: Float, height: Float): GameObject {
        return GameObject(Transform(Vector2f(), 0.0f, Vector2f(width, height)), 0, "SpriteObjectGen").apply {
            this.addComponent(SpriteRenderer().apply { this.sprite = sprite })
        }
    }

}