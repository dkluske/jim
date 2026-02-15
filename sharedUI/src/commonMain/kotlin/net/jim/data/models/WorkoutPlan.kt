package net.jim.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.jim.sqldelight.Workout_plans
import kotlin.uuid.Uuid

@Serializable
@SerialName("WorkoutPlan")
data class WorkoutPlan(
    override val id: Uuid,
    val name: String,
    val default: Boolean,
    val workoutPlanPartIds: List<Uuid>
) : Entity<Uuid, Workout_plans> {
    override fun toDB(): Workout_plans {
        return Workout_plans(
            id = id,
            name = name,
            default_plan = default,
            workout_plan_ids = workoutPlanPartIds
        )
    }

    companion object : EntityConvertable<Workout_plans, WorkoutPlan> {
        override fun fromDB(db: Workout_plans): WorkoutPlan {
            return WorkoutPlan(
                id = db.id,
                name = db.name,
                default = db.default_plan,
                workoutPlanPartIds = db.workout_plan_ids
            )
        }
    }
}
