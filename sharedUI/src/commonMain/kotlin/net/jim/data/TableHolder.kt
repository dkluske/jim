package net.jim.data

import net.jim.data.table.*
import net.jim.sqldelight.JimRuntimeDatabase

class TableHolder(database: JimRuntimeDatabase) {
    val jsonExerciseTable: JsonExerciseTable = JsonExerciseTable(database)

    val workoutEntryExerciseTable: WorkoutEntryExerciseTable = WorkoutEntryExerciseTable(database)

    val workoutEntryTable: WorkoutEntryTable = WorkoutEntryTable(database)

    val workoutPlanPartTable: WorkoutPlanPartTable = WorkoutPlanPartTable(database)

    val workoutPlanTable: WorkoutPlanTable = WorkoutPlanTable(database)
}