package net.jim.data.models

import kotlinx.datetime.DayOfWeek
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
    val dayOfWeek: DayOfWeek,
    val exercises: List<WorkoutPlanExercise>
) : Entity<Uuid, Workout_plan_parts> {
    override fun toDB(): Workout_plan_parts {
        return Workout_plan_parts(
            id = id,
            name = name,
            workout_plan_id = workoutPlanId,
            day_of_week = dayOfWeek,
            exercises = exercises
        )
    }

    companion object : EntityConvertable<Workout_plan_parts, WorkoutPlanPart> {
        override fun fromDB(db: Workout_plan_parts): WorkoutPlanPart {
            return WorkoutPlanPart(
                id = db.id,
                workoutPlanId = db.workout_plan_id,
                name = db.name,
                dayOfWeek = db.day_of_week,
                exercises = db.exercises
            )
        }
    }
}
