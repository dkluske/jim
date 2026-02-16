package net.jim.data.models

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.jim.sqldelight.Workout_entry_exercises
import kotlin.uuid.Uuid

@Serializable
@SerialName("WorkoutEntryExercise")
data class WorkoutEntryExercise(
    override val id: Uuid,
    val workoutPlanPartId: Uuid,
    val workoutEntryId: Uuid,
    val execution: ExerciseExecution
) : Entity<Uuid, Workout_entry_exercises> {
    override fun toDB(): Workout_entry_exercises {
        return Workout_entry_exercises(
            id = id,
            workout_plan_part_id = workoutPlanPartId,
            workout_entry_id = workoutEntryId,
            execution = execution
        )
    }

    @Serializable
    @Polymorphic
    @SerialName("WorkoutEntryExercise.ExerciseExecution")
    sealed interface ExerciseExecution

    @Serializable
    @SerialName("WorkoutEntryExercise.WeighedRepetitionExerciseExecution")
    data class WeighedRepetitionExerciseExecution(
        val sets: Map<Int, Int> // Map<Repetitions, Weight>
    ) : ExerciseExecution

    companion object : EntityConvertable<Workout_entry_exercises, WorkoutEntryExercise> {
        override fun fromDB(db: Workout_entry_exercises): WorkoutEntryExercise {
            return WorkoutEntryExercise(
                id = db.id,
                workoutPlanPartId = db.workout_plan_part_id,
                workoutEntryId = db.workout_entry_id,
                execution = db.execution,
            )
        }
    }
}
