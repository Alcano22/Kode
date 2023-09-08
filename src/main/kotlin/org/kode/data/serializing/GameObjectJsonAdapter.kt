package org.kode.data.serializing

import com.google.gson.*
import org.kode.gameobject.GameObject
import org.kode.gameobject.Transform
import org.kode.gameobject.component.Component
import java.lang.reflect.Type

class GameObjectJsonAdapter : JsonDeserializer<GameObject> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): GameObject {
        val jsonObj = json.asJsonObject
        val name = jsonObj["name"].asString
        val components = jsonObj["components"].asJsonArray
        val transform = context.deserialize<Transform>(jsonObj["transform"], Transform::class.java)
        val zIndex = context.deserialize<Int>(jsonObj["zIndex"], Int::class.java)

        return GameObject(transform, zIndex, name).apply {
            components.forEach {
                val component = context.deserialize<Component>(it, Component::class.java)
                this.addComponent(component)
            }
        }
    }
}