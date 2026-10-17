package net.jim.data.models.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class SerializableListAdapter<T : @Serializable Any>(val json: Json, serializer: KSerializer<T>) :
    ColumnAdapter<List<T>, String> {
    private val listSerializer = ListSerializer(serializer)

    override fun decode(databaseValue: String): List<T> {
        return json.decodeFromString(deserializer = listSerializer, string = databaseValue)
    }

    override fun encode(value: List<T>): String {
        return json.encodeToString(
            serializer = listSerializer,
            value = value
        )
    }
}