package org.kode.rendering

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.kode.core.Window
import org.kode.debug.Log
import org.kode.math.*

class Camera(var position: Vector2f = Vector2f(0.0f, 0.0f)) {

    val projectionMatrix: Matrix4f = Matrix4f()
    val viewMatrix: Matrix4f = Matrix4f()
        get() {
            val camFront = Vector3f(0.0f, 0.0f, -1.0f)
            val camUp = Vector3f(0.0f, 1.0f, 0.0f)
            field.identity()
            field.lookAt(
                Vector3f(this.position.x, this.position.y, 20.0f),
                camFront + Vector3f(position.x, position.y, 0.0f),
                camUp
            )
            field.invert(this.inverseViewMatrix)
            return field
        }

    private var inverseProjectionMatrix = Matrix4f()
    private var inverseViewMatrix = Matrix4f()

    init {
        this.adjustProjection()
    }

    fun adjustProjection() {
        this.projectionMatrix.identity()
        this.projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f)
        this.projectionMatrix.invert(this.inverseProjectionMatrix)
    }

    fun screenToWorldPosition(screenPosition: Vector2f): Vector2f {
        val x = (screenPosition.x / Window.width.toFloat()) * 2.0f - 1.0f
        val y = ((Window.height.toFloat() - screenPosition.y) / Window.height.toFloat()) * 2.0f - 1.0f
        val tmp = Vector4f(x, y, 0.0f, 1.0f)
        tmp.mul(this.inverseProjectionMatrix).mul(this.inverseViewMatrix)

        return Vector2f(tmp.x, tmp.y)
    }

}