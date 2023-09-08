package org.kode.rendering

import org.joml.Vector2f

class Sprite {

    var texture: Texture? = null
    var texCoords: Array<Vector2f> = arrayOf(
        Vector2f(1.0f, 1.0f),
        Vector2f(1.0f, 0.0f),
        Vector2f(0.0f, 0.0f),
        Vector2f(0.0f, 1.0f)
    )

    val texId: Int?
        get() = this.texture?.texId
    val width: Int?
        get() = this.texture?.width
    val height: Int?
        get() = this.texture?.height

}