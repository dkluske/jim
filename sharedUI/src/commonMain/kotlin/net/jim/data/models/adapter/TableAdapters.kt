package net.jim.data.models.adapter

import app.cash.sqldelight.EnumColumnAdapter
import net.jim.sqldelight.Json_exercises

@Suppress("UNCHECKED_CAST")
object TableAdapters {
    val physicalJsonExerciseAdapter: Json_exercises.Adapter = Json_exercises.Adapter(
        idAdapter = UuidAdapter(),
        levelAdapter = EnumColumnAdapter(),
        categoryAdapter = EnumColumnAdapter(),
        documentAdapter = JsonExerciseTypeDocumentAdapter()
    )
}