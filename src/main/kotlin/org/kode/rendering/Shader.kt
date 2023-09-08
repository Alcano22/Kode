package org.kode.rendering

import org.joml.*
import org.kode.debug.Log
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*
import java.io.File
import java.io.IOException

class Shader(private val filepath: String) {

    companion object {
        const val TYPE_PREFIX = "#type"
    }

    private var programId: Int = 0
    private var isBeingUsed: Boolean = false
    private lateinit var vertexSrc: String
    private lateinit var fragmentSrc: String

    init {
        try {
            val src = File(this.filepath).readText()
            val splitSrc = src.split(Regex("$TYPE_PREFIX\\s+[a-zA-Z]+"))

            var index = src.indexOf(TYPE_PREFIX) + 6
            var eol = src.indexOf("\r\n", index)
            val firstPattern = src.substring(index, eol).trim()

            index = src.indexOf(TYPE_PREFIX, eol) + 6
            eol = src.indexOf("\r\n", index)
            val secondPattern = src.substring(index, eol).trim()

            when (firstPattern) {
                "vertex" -> this.vertexSrc = splitSrc[1]
                "fragment" -> this.fragmentSrc = splitSrc[1]
                else -> error("Shader: Unexpected token '$firstPattern' in '${this.filepath}'")
            }

            when (secondPattern) {
                "vertex" -> this.vertexSrc = splitSrc[2]
                "fragment" -> this.fragmentSrc = splitSrc[2]
                else -> error("Shader: Unexpected token '$secondPattern' in '${this.filepath}'")
            }
        } catch (e: IOException) {
            Log.error("Shader: Could not open file for shader '${this.filepath}'\n\t${e.stackTrace}")
        }
    }

    fun compile() {
        val vertexId = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexId, this.vertexSrc)
        glCompileShader(vertexId)

        var success = glGetShaderi(vertexId, GL_COMPILE_STATUS)
        if (success == GL_FALSE) {
            val len = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH)
            val log = glGetShaderInfoLog(vertexId, len)
            error("Shader '${this.filepath}':\n\tVertex shader compilation failed.\n\t$log")
        }

        val fragmentId = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentId, this.fragmentSrc)
        glCompileShader(fragmentId)

        success = glGetShaderi(fragmentId, GL_COMPILE_STATUS)
        if (success == GL_FALSE) {
            val len = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH)
            val log = glGetShaderInfoLog(fragmentId, len)
            error("Shader '${this.filepath}':\n\tFragment shader compilation failed.\n\t$log")
        }

        this.programId = glCreateProgram()
        glAttachShader(this.programId, vertexId)
        glAttachShader(this.programId, fragmentId)
        glLinkProgram(this.programId)

        success = glGetProgrami(this.programId, GL_LINK_STATUS)
        if (success == GL_FALSE) {
            val len = glGetProgrami(this.programId, GL_INFO_LOG_LENGTH)
            val log = glGetProgramInfoLog(this.programId, len)
            error("Shader '${this.filepath}':\n\tLinking of shaders failed.\n\t$log")
        }
    }

    fun use() {
        if (this.isBeingUsed) return

        glUseProgram(this.programId)
        this.isBeingUsed = true
    }

    fun detach() {
        if (!this.isBeingUsed) return

        glUseProgram(0)
        this.isBeingUsed = false
    }

    fun <T> uploadUniform(varName: String, value: T) {
        val varLoc = glGetUniformLocation(this.programId, varName)
        when (value) {
            is Matrix4f -> {
                val matBuf = BufferUtils.createFloatBuffer(16)
                value.get(matBuf)
                glUniformMatrix4fv(varLoc, false, matBuf)
            }
            is Matrix3f -> {
                val matBuf = BufferUtils.createFloatBuffer(9)
                value.get(matBuf)
                glUniformMatrix3fv(varLoc, false, matBuf)
            }
            is Matrix2f -> {
                val matBuf = BufferUtils.createFloatBuffer(4)
                value.get(matBuf)
                glUniformMatrix2fv(varLoc, false, matBuf)
            }
            is Vector4i -> glUniform4i(varLoc, value.x, value.y, value.z, value.w)
            is Vector3i -> glUniform3i(varLoc, value.x, value.y, value.z)
            is Vector2i -> glUniform2i(varLoc, value.x, value.y)
            is Vector4f -> glUniform4f(varLoc, value.x, value.y, value.z, value.w)
            is Vector3f -> glUniform3f(varLoc, value.x, value.y, value.z)
            is Vector2f -> glUniform2f(varLoc, value.x, value.y)
            is IntArray -> glUniform1iv(varLoc, value)
            is FloatArray -> glUniform1fv(varLoc, value)
            is Float -> glUniform1f(varLoc, value)
            is Int -> glUniform1i(varLoc, value)
            else -> throw IllegalArgumentException("Unsupported uniform type")
        }
    }

    fun uploadTexture(varName: String, slot: Int) {
        this.uploadUniform(varName, slot)
    }

}