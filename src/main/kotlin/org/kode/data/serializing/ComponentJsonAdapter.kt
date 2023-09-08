package org.kode.data.serializing

import com.google.gson.*
import org.kode.gameobject.component.Component
import java.lang.reflect.Type

class ComponentJsonAdapter : JsonSerializer<Component>, JsonDeserializer<Component> {
    override fun serialize(src: Component, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObj = JsonObject()
        jsonObj.add("type", JsonPrimitive(src.javaClass.canonicalName))
        jsonObj.add("properties", context.serialize(src, src.javaClass))
        return jsonObj
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Component {
        val jsonObj = json.asJsonObject
        val type = jsonObj["type"].asString
        val element = jsonObj["properties"]

        return context.deserialize(element, Class.forName(type))
    }
}