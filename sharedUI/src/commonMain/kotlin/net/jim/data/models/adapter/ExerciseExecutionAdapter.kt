package net.jim.data.models.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import net.jim.data.models.WorkoutEntryExercise

class ExerciseExecutionAdapter(val json: Json) : ColumnAdapter<WorkoutEntryExercise.ExerciseExecution, String> {
    override fun decode(databaseValue: String): WorkoutEntryExercise.ExerciseExecution {
        return json.decodeFromString<WorkoutEntryExercise.ExerciseExecution>(databaseValue)
    }

    override fun encode(value: WorkoutEntryExercise.ExerciseExecution): String {
        return json.encodeToString(value)
    }
}