package com.example.sampleapp.adapters.gson

import com.example.sampleapp.consts.OvalColor
import com.google.gson.*
import java.lang.reflect.Type

class ElementTypeJsonAdapter : JsonDeserializer<OvalColor>,JsonSerializer<OvalColor>{
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): OvalColor {
        return OvalColor.values()[json.asInt]
    }

    override fun serialize(src: OvalColor, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.ordinal)
    }

}
