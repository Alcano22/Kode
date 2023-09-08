package org.kode.imgui

import imgui.ImGui
import imgui.flag.ImGuiInputTextFlags
import imgui.type.ImString
import org.joml.*
import org.kode.util.Color

object ImGuiTypes {

    inline fun <reified T> any(name: String = "", value: T, disabled: Boolean = false): T? {
        return when (T::class) {
            Int::class -> this.int(name, value as Int, disabled) as T
            Float::class -> this.float(name, value as Float, disabled) as T
            Boolean::class -> this.boolean(name, value as Boolean, disabled) as T
            String::class -> this.string(name, value as String, disabled) as T
            Vector2i::class -> this.vec2i(name, value as Vector2i, disabled) as T
            Vector3i::class -> this.vec3i(name, value as Vector3i, disabled) as T
            Vector4i::class -> this.vec4i(name, value as Vector4i, disabled) as T
            Vector2f::class -> this.vec2f(name, value as Vector2f, disabled) as T
            Vector3f::class -> this.vec3f(name, value as Vector3f, disabled) as T
            Vector4f::class -> this.vec4f(name, value as Vector4f, disabled) as T
            Color::class -> this.color(name, value as Color, disabled) as T
            else -> null
        }
    }

    fun int(name: String = "", int: Int, disabled: Boolean = false): Int {
        val imInt = intArrayOf(int)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragInt(name, imInt)) imInt[0] else int
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun float(name: String = "", float: Float, disabled: Boolean = false): Float {
        val imFloat = floatArrayOf(float)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragFloat(name, imFloat)) imFloat[0] else float
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun boolean(name: String = "", boolean: Boolean, disabled: Boolean = false): Boolean {
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.checkbox(name, boolean)) !boolean else boolean
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun string(name: String = "", string: String, disabled: Boolean = false, hint: String = ""): String {
        val imString = ImString(string)
        val value: String
        if (disabled) {
            ImGui.beginDisabled()
        }
        if (hint.isNotEmpty()) {
            value = if (ImGui.inputTextWithHint(name, hint, imString, ImGuiInputTextFlags.CallbackResize)) imString.get() else string
        } else {
            value = if (ImGui.inputText(name, imString, ImGuiInputTextFlags.CallbackResize)) imString.get() else string
        }
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

//    fun <T : Enum<T>> enum(name: String, value: T): T {
//
//    }

    fun vec2i(name: String = "", vec2i: Vector2i, disabled: Boolean = false): Vector2i {
        val imVec2i = intArrayOf(vec2i.x, vec2i.y)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragInt2(name, imVec2i)) Vector2i(imVec2i[0], imVec2i[1]) else vec2i
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun vec3i(name: String = "", vec3i: Vector3i, disabled: Boolean = false): Vector3i {
        val imVec3i = intArrayOf(vec3i.x, vec3i.y, vec3i.z)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragInt2(name, imVec3i)) Vector3i(imVec3i[0], imVec3i[1], imVec3i[2]) else vec3i
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun vec4i(name: String = "", vec4i: Vector4i, disabled: Boolean = false): Vector4i {
        val imVec4i = intArrayOf(vec4i.x, vec4i.y, vec4i.z, vec4i.w)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragInt2(name, imVec4i)) Vector4i(imVec4i[0], imVec4i[1], imVec4i[2], imVec4i[3]) else vec4i
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun vec2f(name: String = "", vec2f: Vector2f, disabled: Boolean = false): Vector2f {
        val imVec2f = floatArrayOf(vec2f.x, vec2f.y)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragFloat2(name, imVec2f)) Vector2f(imVec2f[0], imVec2f[1]) else vec2f
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun vec3f(name: String = "", vec3f: Vector3f, disabled: Boolean = false): Vector3f {
        val imVec3f = floatArrayOf(vec3f.x, vec3f.y, vec3f.z)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragFloat3(name, imVec3f)) Vector3f(imVec3f[0], imVec3f[1], imVec3f[2]) else vec3f
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun vec4f(name: String = "", vec4f: Vector4f, disabled: Boolean = false): Vector4f {
        val imVec4f = floatArrayOf(vec4f.x, vec4f.y, vec4f.z, vec4f.w)
        if (disabled) {
            ImGui.beginDisabled()
        }
        val value = if (ImGui.dragFloat4(name, imVec4f)) Vector4f(imVec4f[0], imVec4f[1], imVec4f[2], imVec4f[3]) else vec4f
        if (disabled) {
            ImGui.endDisabled()
        }
        return value
    }

    fun color(name: String = "", color: Color, disabled: Boolean = false): Color {
        val imColor = color.toFloatArray()
        if (disabled) {
            ImGui.beginDisabled()
        }
        if (ImGui.colorPicker4(name, imColor)) {
            color.set(floatArrayOf(imColor[0], imColor[1], imColor[2], imColor[3]))
        }
        if (disabled) {
            ImGui.endDisabled()
        }
        return color
    }

    inline fun <reified T> list(name: String = "", list: MutableList<T>, default: T, disabledIndices: IntArray): MutableList<T> {
        if (!ImGui.beginTable(name, 2)) return list

        for (i in list.indices) {
            ImGui.tableNextRow()
            ImGui.tableNextColumn()

            val disable = disabledIndices.contains(i)

            list[i] = this.any("##$i", list[i], disable)!!

            ImGui.sameLine()
            if (disable) {
                ImGui.beginDisabled()
            }
            ImGui.tableNextColumn()
            if (ImGui.button("Remove##$i")) {
                list.removeAt(i)
                break
            }
            if (disable) {
                ImGui.endDisabled()
            }
        }

        ImGui.endTable()

        if (ImGui.button("Add##$name")) {
            list.add(default)
        }

        return list
    }

    inline fun <reified T> list(name: String = "", list: MutableList<T>, default: T, disabledValues: Array<T> = emptyArray()): MutableList<T> =
        this.list<T>(name, list, default, disabledValues.map { list.indexOf(it) }.toIntArray())

}