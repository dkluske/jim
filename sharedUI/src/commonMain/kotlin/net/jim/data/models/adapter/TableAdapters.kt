package net.jim.data.models.adapter

import app.cash.sqldelight.EnumColumnAdapter
import net.jim.data.JimDatabaseManager
import net.jim.data.models.WorkoutPlanExercise
import net.jim.sqldelight.Json_exercises
import net.jim.sqldelight.Workout_entries
import net.jim.sqldelight.Workout_plan_parts
import net.jim.sqldelight.Workout_plans
import kotlin.reflect.KClass
import kotlin.uuid.Uuid

@Suppress("UNCHECKED_CAST")
object TableAdapters {
    val physicalJsonExerciseAdapter: Json_exercises.Adapter = Json_exercises.Adapter(
        idAdapter = UuidAdapter(),
        levelAdapter = EnumColumnAdapter(),
        categoryAdapter = EnumColumnAdapter(),
        documentAdapter = JsonExerciseTypeDocumentAdapter()
    )

    val workoutPlansAdapter: Workout_plans.Adapter = Workout_plans.Adapter(
        idAdapter = UuidAdapter(),
        workout_plan_idsAdapter = SerializableListAdapter(
            json = JimDatabaseManager.json,
            clazz = List::class as KClass<List<Uuid>>,
        )
    )

    val workoutPlanPartsAdapter: Workout_plan_parts.Adapter = Workout_plan_parts.Adapter(
        idAdapter = UuidAdapter(),
        workout_plan_idAdapter = UuidAdapter(),
        day_of_weekAdapter = EnumColumnAdapter(),
        exercisesAdapter = SerializableListAdapter(
            json = JimDatabaseManager.json,
            clazz = List::class as KClass<List<WorkoutPlanExercise>>,
        )
    )

    val workoutEntriesAdapter: Workout_entries.Adapter = Workout_entries.Adapter(
        idAdapter = UuidAdapter(),
        start_timeAdapter = InstantAdapter(json = JimDatabaseManager.json),
        finish_timeAdapter = InstantAdapter(json = JimDatabaseManager.json),
        workout_plan_idAdapter = UuidAdapter(),
        workout_plan_part_idAdapter = UuidAdapter()
    )
}