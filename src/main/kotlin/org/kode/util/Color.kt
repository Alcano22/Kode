package org.kode.util

import org.joml.Vector4f
import org.joml.Vector4i

class Color {

    var r: Int = 255
    var g: Int = 255
    var b: Int = 255
    var a: Int = 255

    fun set(vec4i: Vector4i) {
        this.r = vec4i.x
        this.g = vec4i.y
        this.b = vec4i.z
        this.a = vec4i.w
    }

    fun set(vec4f: Vector4f) = this.set(Vector4i((vec4f.x * 255.0f).toInt(), (vec4f.y * 255.0f).toInt(), (vec4f.z * 255.0f).toInt(), (vec4f.w * 255.0f).toInt()))

    fun set(intArray: IntArray) {
        this.r = intArray[0]
        this.g = intArray[1]
        this.b = intArray[2]
        this.a = intArray[3]
    }

    fun set(floatArray: FloatArray) {
        this.set(floatArray.map { (it * 255.0f).toInt() }.toIntArray())
    }

    fun toArray(): IntArray = intArrayOf(this.r, this.g, this.b, this.a)
    fun toFloatArray(): FloatArray = floatArrayOf(this.r.toFloat() / 255.0f, this.g.toFloat() / 255.0f, this.b.toFloat() / 255.0f, this.a.toFloat() / 255.0f)
    fun toVector() = Vector4f().set(this.toFloatArray())
}
