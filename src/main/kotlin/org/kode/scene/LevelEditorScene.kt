package org.kode.scene

import imgui.ImGui
import imgui.ImVec2
import org.joml.Vector2f
import org.kode.data.AssetPool
import org.kode.data.SpriteSheet
import org.kode.debug.Log
import org.kode.gameobject.GameObject
import org.kode.gameobject.Prefabs
import org.kode.gameobject.Transform
import org.kode.gameobject.component.MouseControls
import org.kode.gameobject.component.SpriteRenderer
import org.kode.input.Input

class LevelEditorScene : Scene() {

    private lateinit var blobfish: GameObject

    val mouseControls: MouseControls = MouseControls()

    override fun init() {
        super.init()

        val spritesheet =
            AssetPool.spritesheets.add(SpriteSheet(AssetPool.textures["assets/textures/objects.png"]!!, 16, 16, 0))

        if (this.isLevelLoaded) {
            this.activeGameObject = GameObject.findGameObject("Pumpkin")
            return
        }

        val pumpkin = GameObject(Transform(Vector2f(100.0f, 100.0f), 0.0f, Vector2f(256.0f, 256.0f)), 3, "Pumpkin")
        val pumpkinSpr = SpriteRenderer().apply {
            sprite = spritesheet[12, 11]
        }
        pumpkin.addComponent(pumpkinSpr)
        this.addGameObject(pumpkin)

        this.blobfish = GameObject(Transform(Vector2f(250.0f, 100.0f), 0.0f, Vector2f(256.0f, 256.0f)), 2, "Blobfish")
        this.blobfish.addComponent(SpriteRenderer().apply {
            sprite = spritesheet[8, 33]
        })
        this.addGameObject(this.blobfish)
    }

    override fun imgui() {
        ImGui.begin("Tile Palette")

        val windowPos = ImVec2()
        ImGui.getWindowPos(windowPos)
        val windowSize = ImVec2()
        ImGui.getWindowSize(windowSize)
        val itemSpacing = ImVec2()
        ImGui.getStyle().getItemSpacing(itemSpacing)

        val windowX2 = windowPos.x + windowSize.x

        val tiles = AssetPool.spritesheets["assets/textures/objects.png"]!!
        for (i in 0..<tiles.size) {
            val tile = tiles[i]
            val tileWidth = (tile.width!! * 2).toFloat()
            val tileHeight = (tile.height!! * 2).toFloat()

            if (tile.texture == null) continue

            val id = tile.texId!!
            val texCoords = tile.texCoords

            ImGui.pushID(i)
            if (ImGui.imageButton(id, tileWidth, tileHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                val spriteObj = Prefabs.generateSpriteObject(tile, tile.width!!.toFloat(), tile.height!!.toFloat())
                this.mouseControls.pickupObject(spriteObj)
            }
            ImGui.popID()

            val lastButtonPos = ImVec2()
            ImGui.getItemRectMax(lastButtonPos)
            val lastButtonX2 = lastButtonPos.x
            val nextButtonX2 = lastButtonX2 + itemSpacing.x + tileWidth
            if (tiles.size >= i + 1 && nextButtonX2 < windowX2) {
                ImGui.sameLine()
            }
        }

        ImGui.end()
    }

    override fun update() {
        this.mouseControls.update()

        super.update()
    }
}