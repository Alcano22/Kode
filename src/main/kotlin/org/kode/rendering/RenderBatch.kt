package org.kode.rendering

import org.kode.data.AssetPool
import org.kode.gameobject.component.SpriteRenderer
import org.kode.scene.SceneManager
import org.lwjgl.opengl.GL30.*

class RenderBatch(private val maxBatchSize: Int, val zIndex: Int = 0) : Comparable<RenderBatch> {

    // Vertex
    // Pos                  // Color                        // Tex Coords       // Tex ID
    // float, float,        float, float, float, float      float, float        float

    companion object {
        const val POS_SIZE: Int = 2
        const val COLOR_SIZE: Int = 4
        const val TEX_COORDS_SIZE: Int = 2
        const val TEX_ID_SIZE: Int = 1

        const val VERTEX_SIZE: Int = 9
        const val POS_OFFSET: Long = 0L
        const val COLOR_OFFSET: Long = POS_OFFSET + POS_SIZE * Float.SIZE_BYTES
        const val TEX_COORDS_OFFSET: Long = COLOR_OFFSET + COLOR_SIZE * Float.SIZE_BYTES
        const val TEX_ID_OFFSET: Long = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.SIZE_BYTES
        const val VERTEX_SIZE_BYTES: Int = VERTEX_SIZE * Float.SIZE_BYTES

        const val TEX_LIMIT: Int = 8
    }

    private val shader: Shader = AssetPool.shaders["assets/shaders/default.glsl"]!!
    private val sprites: Array<SpriteRenderer?> = Array(this.maxBatchSize) { null }
    private val textures: MutableList<Texture> = ArrayList()
    private val texSlots: IntArray = IntArray(TEX_LIMIT) { it }
    private var numSprites: Int = 0;

    private var vertices: FloatArray = FloatArray(this.maxBatchSize * 4 * VERTEX_SIZE)
    private var vaoId: Int = 0
    private var vboId: Int = 0

    val hasRoom: Boolean
        get() = this.numSprites < this.maxBatchSize
    val hasTextureRoom: Boolean
        get() = this.textures.size < TEX_LIMIT

    init {
        this.shader.compile()
    }

    fun start() {
        this.vaoId = glGenVertexArrays()
        glBindVertexArray(this.vaoId)

        this.vboId = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, this.vboId)
        glBufferData(GL_ARRAY_BUFFER, (this.vertices.size * Float.SIZE_BYTES).toLong(), GL_DYNAMIC_DRAW)

        val eboId = glGenBuffers()
        val indices = this.generateIndices()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET)
        glEnableVertexAttribArray(0)

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET)
        glEnableVertexAttribArray(1)

        glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET)
        glEnableVertexAttribArray(2)

        glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET)
        glEnableVertexAttribArray(3)
    }

    fun addSprite(spr: SpriteRenderer) {
        val index = this.numSprites
        this.sprites[index] = spr
        this.numSprites++

        if (spr.texture != null && !this.textures.contains(spr.texture)) {
            this.textures.add(spr.texture!!)
        }

        this.loadVertexProperties(index)
    }

    fun render() {
        var shouldRebufferData = false
        for (i in this.sprites.indices) {
            val spr = this.sprites[i] ?: continue
            if (!spr.isDirty) continue

            this.loadVertexProperties(i)
            spr.isDirty = false
            shouldRebufferData = true
        }

        if (shouldRebufferData) {
            glBindBuffer(GL_ARRAY_BUFFER, this.vboId)
            glBufferSubData(GL_ARRAY_BUFFER, 0L, this.vertices)
        }

        this.shader.use()

        val camera = SceneManager.currentScene!!.camera
        this.shader.uploadUniform("u_Projection", camera.projectionMatrix)
        this.shader.uploadUniform("u_View", camera.viewMatrix)

        for (i in this.textures.indices) {
            glActiveTexture(GL_TEXTURE0 + i + 1)
            this.textures[i].bind()
        }
        this.shader.uploadUniform("u_Textures", this.texSlots)

        glBindVertexArray(this.vaoId)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0L)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)

        for (i in this.textures.indices) {
            this.textures[i].unbind()
        }

        this.shader.detach()
    }

    private fun loadVertexProperties(index: Int) {
        var offset = index * 4 * VERTEX_SIZE

        val spr = this.sprites[index]!!
        val color = spr.color.toVector()
        val texCoords = spr.texCoords

        var texId = 0
        if (spr.texture != null) {
            for (i in this.textures.indices) {
                if (this.textures[i] == spr.texture) {
                    texId = i + 1
                    break
                }
            }
        }

        var addX = 1.0f
        var addY = 1.0f
        for (i in 0..<4) {
            when (i) {
                1 -> addY = 0.0f
                2 -> addX = 0.0f
                3 -> addY = 1.0f
            }

            this.vertices[offset] = spr.gameObject.transform.position.x + (addX * spr.gameObject.transform.scale.x)
            this.vertices[offset + 1] = spr.gameObject.transform.position.y + (addY * spr.gameObject.transform.scale.y)

            this.vertices[offset + 2] = color.x
            this.vertices[offset + 3] = color.y
            this.vertices[offset + 4] = color.z
            this.vertices[offset + 5] = color.w

            this.vertices[offset + 6] = texCoords[i].x
            this.vertices[offset + 7] = texCoords[i].y

            this.vertices[offset + 8] = texId.toFloat()

            offset += VERTEX_SIZE
        }
    }

    private fun generateIndices(): IntArray {
        val elements = IntArray(6 * this.maxBatchSize)
        for (i in 0..<this.maxBatchSize) {
            this.loadElementIndices(elements, i)
        }

        return elements
    }

    private fun loadElementIndices(elements: IntArray, index: Int) {
        val offsetArrayIndex = 6 * index
        val offset = 4 * index

        elements[offsetArrayIndex] = offset + 3
        elements[offsetArrayIndex + 1] = offset + 2
        elements[offsetArrayIndex + 2] = offset
        elements[offsetArrayIndex + 3] = offset
        elements[offsetArrayIndex + 4] = offset + 2
        elements[offsetArrayIndex + 5] = offset + 1
    }

    fun hasTexture(tex: Texture): Boolean = this.textures.contains(tex)

    override fun compareTo(other: RenderBatch): Int {
        return this.zIndex.compareTo(other.zIndex)
    }

}