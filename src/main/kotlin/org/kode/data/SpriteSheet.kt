package org.kode.data

import org.joml.Vector2f
import org.kode.debug.Log
import org.kode.rendering.Sprite
import org.kode.rendering.Texture

class SpriteSheet(private val texture: Texture, spriteWidth: Int, spriteHeight: Int, spacing: Int) : IComplexAsset {

    private val sprites: MutableList<Sprite> = ArrayList()

    val columns: Int
    val rows: Int
    val size: Int
        get() = this.columns * this.rows

    init {
        this.columns = this.texture.width / spriteWidth
        this.rows = this.texture.height / spriteHeight
        val numSprites = this.columns * this.rows

        var currentX = 0
        var currentY = this.texture.height - spriteHeight
        for (i in 0..<numSprites) {
            val topY = (currentY + spriteHeight) / this.texture.height.toFloat()
            val rightX = (currentX + spriteWidth) / this.texture.width.toFloat()
            val leftX = currentX / texture.width.toFloat()
            val bottomY = currentY / texture.height.toFloat()

            val texCoords = arrayOf(
                Vector2f(rightX, topY),
                Vector2f(rightX, bottomY),
                Vector2f(leftX, bottomY),
                Vector2f(leftX, topY)
            )
            val sprite = Sprite().apply {
                this.texture = this@SpriteSheet.texture
                this.texCoords = texCoords
                this.texture!!.width = spriteWidth
                this.texture!!.height = spriteHeight
            }
            this.sprites.add(sprite)

            currentX += spriteWidth + spacing
            if (currentX >= texture.width) {
                currentX = 0
                currentY -= spriteHeight + spacing
            }
        }
    }

    operator fun get(index: Int): Sprite = this.sprites[index]
    operator fun get(x: Int, y: Int): Sprite = this.sprites[y * this.columns + x]

    override val assetFilepath: String
        get() = this.texture.filepath

}