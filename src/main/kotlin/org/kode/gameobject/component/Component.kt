package org.kode.gameobject.component

import com.google.gson.annotations.JsonAdapter
import imgui.ImGui
import org.joml.*
import org.kode.data.serializing.ComponentJsonAdapter
import org.kode.data.serializing.HideInInspector
import org.kode.data.serializing.SerializeField
import org.kode.gameobject.GameObject
import org.kode.gameobject.Transform
import org.kode.imgui.ImGuiTypes
import org.kode.scene.Scene
import org.kode.scene.SceneManager
import org.kode.util.Color
import org.kode.util.TextUtils
import org.kode.util.get
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField
import kotlin.reflect.typeOf

@JsonAdapter(ComponentJsonAdapter::class)
abstract class Component {

    @Transient
    lateinit var gameObject: GameObject
    val transform: Transform
        get() = gameObject.transform
    val scene: Scene
        get() = SceneManager.currentScene!!

    open fun start() {}
    open fun update() {}

    open fun imgui() {
        val componentName = TextUtils.displayFormatText(this::class.java.simpleName)
        if (!ImGui.collapsingHeader(componentName)) return

        val properties = this::class.declaredMemberProperties
        for (property in properties.reversed()) {
            if (property !is KMutableProperty1) continue

            if (property.javaField!!.isAnnotationPresent(HideInInspector::class.java)) continue

            val isPublic = property.visibility == KVisibility.PUBLIC
            if (!isPublic && !property.javaField!!.isAnnotationPresent(SerializeField::class.java)) continue

            if (!isPublic) {
                property.isAccessible = true
            }

            val type = property.returnType
            val value = property.call(this)
            val name = TextUtils.displayFormatText(property.name)

            val newValue = when (type) {
                typeOf<Int>() -> ImGuiTypes.int(name, value as Int)
                typeOf<Float>() -> ImGuiTypes.float(name, value as Float)
                typeOf<Boolean>() -> ImGuiTypes.boolean(name, value as Boolean)
                typeOf<String>() -> ImGuiTypes.string(name, value as String)
                typeOf<Vector2i>() -> ImGuiTypes.vec2i(name, value as Vector2i)
                typeOf<Vector3i>() -> ImGuiTypes.vec3i(name, value as Vector3i)
                typeOf<Vector4i>() -> ImGuiTypes.vec4i(name, value as Vector4i)
                typeOf<Vector2f>() -> ImGuiTypes.vec2f(name, value as Vector2f)
                typeOf<Vector3f>() -> ImGuiTypes.vec3f(name, value as Vector3f)
                typeOf<Vector4f>() -> ImGuiTypes.vec4f(name, value as Vector4f)
                typeOf<Color>() -> ImGuiTypes.color(name, value as Color)
                else -> null
            }

            property.setter.call(this, newValue)

            if (!isPublic) {
                property.isAccessible = false
            }
        }
    }

}