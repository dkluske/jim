package net.jim.data.models.adapter

import app.cash.sqldelight.EnumColumnAdapter
import net.jim.data.JimDatabaseManager.json
import net.jim.data.models.JsonExercise
import net.jim.sqldelight.Json_exercises
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object TableAdapters {
    val jsonExerciseAdapter: Json_exercises.Adapter = Json_exercises.Adapter(
        idAdapter = UuidAdapter(),
        forceAdapter = EnumColumnAdapter(),
        levelAdapter = EnumColumnAdapter(),
        mechanicAdapter = EnumColumnAdapter(),
        primary_musclesAdapter = SerializableListAdapter(
            json = json,
            clazz = List::class as KClass<List<JsonExercise.MuscleEnum>>
        ),
        secondary_musclesAdapter = SerializableListAdapter(
            json = json,
            clazz = List::class as KClass<List<JsonExercise.MuscleEnum>>
        ),
        instructionsAdapter = SerializableListAdapter(
            json = json,
            clazz = List::class as KClass<List<String>>
        ),
        categoryAdapter = EnumColumnAdapter()
    )
}