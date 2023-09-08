package org.kode.rendering

import org.kode.debug.Log
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.stb.STBImage.*

class Texture(val filepath: String) {

    private var isBound: Boolean = false

    var texId: Int = -1
        private set
    var width: Int = 0
    var height: Int = 0

    init {
        this.texId = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, this.texId)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        val width = BufferUtils.createIntBuffer(1)
        val height = BufferUtils.createIntBuffer(1)
        val channels = BufferUtils.createIntBuffer(1)

        stbi_set_flip_vertically_on_load(true)
        val image = stbi_load(this.filepath, width, height, channels, 0)
        if (image != null) {
            this.width = width.get(0)
            this.height = height.get(0)

            val mode = when (channels.get(0)) {
                3 -> GL_RGB
                4 -> GL_RGBA
                else -> {
                    Log.error("Unknown number of channels '${channels.get(0)}'")
                    -1
                }
            }

            if (mode != -1) {
                glTexImage2D(GL_TEXTURE_2D, 0, mode, width.get(0), height.get(0), 0, mode, GL_UNSIGNED_BYTE, image)
            }
        } else {
            error("Texture: Could not load image '${this.filepath}'")
        }

        stbi_image_free(image)
    }

    fun bind() {
        if (this.isBound) return

        glBindTexture(GL_TEXTURE_2D, this.texId)
        this.isBound = true
    }

    fun unbind() {
        if (!this.isBound) return

        glBindTexture(GL_TEXTURE_2D, 0)
        this.isBound = false
    }

}