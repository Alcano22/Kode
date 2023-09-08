package org.kode.rendering

import org.kode.gameobject.GameObject
import org.kode.gameobject.component.SpriteRenderer

class SceneRenderer {

    companion object {
        const val MAX_BATCH_SIZE = 1000
    }

    val batches: MutableList<RenderBatch> = ArrayList()

    fun add(gameObject: GameObject) {
        val spr = gameObject.getComponent<SpriteRenderer>() ?: return
        this.add(spr)
    }

    private fun add(spr: SpriteRenderer) {
        for (batch in this.batches) {
            if (!batch.hasRoom || batch.zIndex != spr.gameObject.zIndex) continue

            val tex = spr.texture
            if (tex != null && !(batch.hasTexture(tex) || batch.hasTextureRoom)) continue

            batch.addSprite(spr)
            return
        }

        val newBatch = RenderBatch(MAX_BATCH_SIZE, spr.gameObject.zIndex)
        newBatch.start()
        this.batches.add(newBatch)
        newBatch.addSprite(spr)
        this.batches.sort()
    }

    fun render() {
        this.batches.forEach { it.render() }
    }

}