package org.kode.data

import org.kode.debug.Log
import org.kode.rendering.Shader
import org.kode.rendering.Texture
import java.io.File

object AssetPool {

    val files: AssetLoader<File?> = object : AssetLoader<File?>("File") {
        override fun loadAsset(filepath: String): File = File(filepath)
    }

    val shaders: AssetLoader<Shader?> = object : AssetLoader<Shader?>("Shader") {
        override fun loadAsset(filepath: String): Shader = Shader(filepath)
    }

    val textures: AssetLoader<Texture?> = object : AssetLoader<Texture?>("Texture") {
        override fun loadAsset(filepath: String): Texture = Texture(filepath)
    }

    val spritesheets: ComplexAssetLoader<SpriteSheet> = ComplexAssetLoader("SpriteSheet")

    abstract class AssetLoader<T>(protected val typename: String) {
        val assets: MutableMap<String, T> = HashMap()

        operator fun get(filepath: String): T? {
            if (this.assets.containsKey(filepath)) {
                return this.assets[filepath]
            }

            val asset = this.loadAsset(filepath)
            Log.info("Loaded ${this.typename} '$filepath'")
            this.assets[filepath] = asset
            return asset
        }

        protected abstract fun loadAsset(filepath: String): T
    }

    class ComplexAssetLoader<T : IComplexAsset>(typename: String) : AssetLoader<T?>(typename) {
        override fun loadAsset(filepath: String): T? {
            Log.error("${this.typename} '$filepath' has not been added to the AssetPool")
            return null
        }

        fun add(asset: T): T {
            this.assets[asset.assetFilepath] = asset
            return asset
        }
    }

}