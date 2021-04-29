package hu.korsosrichard.demoapp.api

import com.google.gson.*
import java.lang.reflect.Type

internal class BooleanTypeAdapter : JsonDeserializer<Boolean>, JsonSerializer<Boolean> {
    override fun serialize(
        src: Boolean?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(true.equals(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, typeOfT: Type,
        context: JsonDeserializationContext
    ): Boolean? {
        val code = json.asInt
        return if (code == 0)
            false
        else if (code == 1)
            true
        else
            null
    }
}