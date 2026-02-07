package net.jim.components.utils

import jim.sharedui.generated.resources.Res
import kotlinx.serialization.json.Json
import net.jim.data.models.JsonExercise

const val JSON_EXERCISE_REVISION = 0L

val JSON_EXERCISE_JSON = Json {
    ignoreUnknownKeys = true
}

suspend fun readExercises(revision: Long = 0L): List<JsonExercise> {
    val bytes = Res.readBytes("files/exercises/exercises_$revision.json")
    return JSON_EXERCISE_JSON.decodeFromString<List<JsonExercise>>(bytes.decodeToString())
}