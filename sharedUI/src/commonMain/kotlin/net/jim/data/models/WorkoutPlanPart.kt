package net.jim.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.jim.sqldelight.Workout_plan_parts
import kotlin.uuid.Uuid

@Serializable
@SerialName("WorkoutPlanPart")
data class WorkoutPlanPart(
    override val id: Uuid,
    val workoutPlanId: Uuid,
    val name: String,
    val quantity: Int,
    val exercises: List<WorkoutPlanExercise>
) : Entity<Uuid, Workout_plan_parts> {
    override fun toDB(): Workout_plan_parts {
        return Workout_plan_parts(
            id = id,
            name = name,
            workout_plan_id = workoutPlanId,
            quantity = quantity.toLong(),
            exercises = exercises
        )
    }

    companion object : EntityConvertable<Workout_plan_parts, WorkoutPlanPart> {
        override fun fromDB(db: Workout_plan_parts): WorkoutPlanPart {
            return WorkoutPlanPart(
                id = db.id,
                workoutPlanId = db.workout_plan_id,
                name = db.name,
                quantity = db.quantity.toInt(),
                exercises = db.exercises
            )
        }
    }
}
