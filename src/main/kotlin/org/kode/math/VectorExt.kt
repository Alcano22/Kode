package org.kode.math

import org.joml.Vector2f
import org.joml.Vector2i
import org.joml.Vector3f
import org.joml.Vector3i
import org.joml.Vector4f
import org.joml.Vector4i

// Vector2i
operator fun Vector2i.unaryPlus() = this
operator fun Vector2i.unaryMinus() = Vector2i(-this.x, -this.y)
operator fun Vector2i.plus(vec2i: Vector2i) = Vector2i(this.x + vec2i.x, this.y + vec2i.y)
operator fun Vector2i.plus(vec2f: Vector2f) = Vector2f(this.x + vec2f.x, this.y + vec2f.y)
operator fun Vector2i.minus(vec2i: Vector2i) = Vector2i(this.x - vec2i.x, this.y - vec2i.y)
operator fun Vector2i.minus(vec2f: Vector2f) = Vector2f(this.x - vec2f.x, this.y - vec2f.y)
operator fun Vector2i.times(int: Int) = Vector2i(this.x * int, this.y * int)
operator fun Vector2i.times(float: Float) = Vector2f(this.x * float, this.y * float)
operator fun Vector2i.div(int: Int) = Vector2i(this.x / int, this.y / int)
operator fun Vector2i.div(float: Float) = Vector2f(this.x / float, this.y / float)

// Vector3i
operator fun Vector3i.unaryPlus() = this
operator fun Vector3i.unaryMinus() = Vector3i(-this.x, -this.y, -this.z)
operator fun Vector3i.plus(vec3i: Vector3i) = Vector3i(this.x + vec3i.x, this.y + vec3i.y, this.z + vec3i.z)
operator fun Vector3i.plus(vec3f: Vector3f) = Vector3f(this.x + vec3f.x, this.y + vec3f.y, this.z + vec3f.z)
operator fun Vector3i.minus(vec3i: Vector3i) = Vector3i(this.x - vec3i.x, this.y - vec3i.y, this.z - vec3i.z)
operator fun Vector3i.minus(vec3i: Vector3f) = Vector3f(this.x - vec3i.x, this.y - vec3i.y, this.z - vec3i.z)
operator fun Vector3i.times(int: Int) = Vector3i(this.x * int, this.y * int, this.z * int)
operator fun Vector3i.times(float: Float) = Vector3f(this.x * float, this.y * float, this.z * float)
operator fun Vector3i.div(int: Int) = Vector3i(this.x / int, this.y / int, this.z / int)
operator fun Vector3i.div(float: Float) = Vector3f(this.x / float, this.y / float, this.z / float)

// Vector4i
operator fun Vector4i.unaryPlus() = this
operator fun Vector4i.unaryMinus() = Vector4i(-this.x, -this.y, -this.z, -this.w)
operator fun Vector4i.plus(vec4i: Vector4i) = Vector4i(this.x + vec4i.x, this.y + vec4i.y, this.z + vec4i.z, this.w + vec4i.w)
operator fun Vector4i.plus(vec4f: Vector4f) = Vector4f(this.x + vec4f.x, this.y + vec4f.y, this.z + vec4f.z, this.w + vec4f.w)
operator fun Vector4i.minus(vec4i: Vector4i) = Vector4i(this.x - vec4i.x, this.y - vec4i.y, this.z - vec4i.z, this.w - vec4i.w)
operator fun Vector4i.minus(vec4f: Vector4f) = Vector4f(this.x - vec4f.x, this.y - vec4f.y, this.z - vec4f.z, this.w - vec4f.w)
operator fun Vector4i.times(int: Int) = Vector4i(this.x * int, this.y * int, this.z * int, this.w * int)
operator fun Vector4i.times(float: Float) = Vector4f(this.x * float, this.y * float, this.z * float, this.w * float)
operator fun Vector4i.div(int: Int) = Vector4i(this.x / int, this.y / int, this.z / int, this.w / int)
operator fun Vector4i.div(float: Float) = Vector4f(this.x / float, this.y / float, this.z / float, this.w / float)

// Vector2f
operator fun Vector2f.unaryPlus() = this
operator fun Vector2f.unaryMinus() = Vector2f(-this.x, -this.y)
operator fun Vector2f.plus(vec2f: Vector2f) = Vector2f(this.x + vec2f.x, this.y + vec2f.y)
operator fun Vector2f.minus(vec2f: Vector2f) = Vector2f(this.x - vec2f.x, this.y - vec2f.y)
operator fun Vector2f.times(int: Int) = this.times(int.toFloat())
operator fun Vector2f.times(float: Float) = Vector2f(this.x * float, this.y * float)
operator fun Vector2f.div(int: Int) = this.div(int.toFloat())
operator fun Vector2f.div(float: Float) = Vector2f(this.x / float, this.y / float)

// Vector3f
operator fun Vector3f.unaryPlus() = this
operator fun Vector3f.unaryMinus() = Vector3f(-this.x, -this.y, -this.z)
operator fun Vector3f.plus(vec3f: Vector3f) = Vector3f(this.x + vec3f.x, this.y + vec3f.y, this.z + vec3f.z)
operator fun Vector3f.minus(vec3f: Vector3f) = Vector3f(this.x - vec3f.x, this.y - vec3f.y, this.z - vec3f.z)
operator fun Vector3f.times(int: Int) = this.times(int.toFloat())
operator fun Vector3f.times(float: Float) = Vector3f(this.x * float, this.y * float, this.z * float)
operator fun Vector3f.div(int: Int) = this.div(int.toFloat())
operator fun Vector3f.div(float: Float) = Vector3f(this.x / float, this.y / float, this.z / float)

// Vector4f
operator fun Vector4f.unaryPlus() = this
operator fun Vector4f.unaryMinus() = Vector4f(-this.x, -this.y, -this.z, -this.w)
operator fun Vector4f.plus(vec4f: Vector4f) = Vector4f(this.x + vec4f.x, this.y + vec4f.y, this.z + vec4f.z, this.w + vec4f.w)
operator fun Vector4f.minus(vec4f: Vector4f) = Vector4f(this.x - vec4f.x, this.y - vec4f.y, this.z - vec4f.z, this.w - vec4f.w)
operator fun Vector4f.times(int: Int) = this.times(int.toFloat())
operator fun Vector4f.times(float: Float) = Vector4f(this.x * float, this.y * float, this.z * float, this.w * float)
operator fun Vector4f.div(int: Int) = this.div(int.toFloat())
operator fun Vector4f.div(float: Float) = Vector4f(this.x / float, this.y / float, this.z / float, this.w / float)
