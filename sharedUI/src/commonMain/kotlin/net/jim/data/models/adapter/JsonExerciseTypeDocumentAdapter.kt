package net.jim.data.models.adapter

import app.cash.sqldelight.ColumnAdapter
import net.jim.data.JimDatabaseManager
import net.jim.data.models.JsonExerciseType

class JsonExerciseTypeDocumentAdapter : ColumnAdapter<JsonExerciseType, String> {
    override fun decode(databaseValue: String): JsonExerciseType {
        return JimDatabaseManager.json.decodeFromString<JsonExerciseType>(databaseValue)
    }

    override fun encode(value: JsonExerciseType): String {
        return JimDatabaseManager.json.encodeToString(value)
    }
}