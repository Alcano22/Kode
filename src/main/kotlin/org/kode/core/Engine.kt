package org.kode.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object Engine {

    val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    fun run() {
        Window.run()
    }
}

fun main() {
    Engine.run()
}