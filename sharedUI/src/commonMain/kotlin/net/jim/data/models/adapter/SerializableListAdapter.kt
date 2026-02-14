package net.jim.data.models.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class SerializableListAdapter<T : @Serializable Any>(val json: Json, val clazz: KClass<List<T>>) :
    ColumnAdapter<List<T>, String> {
    override fun decode(databaseValue: String): List<T> {
        return json.decodeFromString(deserializer = clazz.serializer(), string = databaseValue)
    }

    override fun encode(value: List<T>): String {
        return json.encodeToString(
            serializer = clazz.serializer(),
            value = value
        )
    }
}