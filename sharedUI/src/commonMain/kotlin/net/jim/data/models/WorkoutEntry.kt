package net.jim.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.jim.sqldelight.Workout_entries
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Serializable
@SerialName("WorkoutEntry")
data class WorkoutEntry(
    override val id: Uuid,
    val startTime: Instant,
    val finishTime: Instant?,
    val workoutPlanId: Uuid,
    val workoutPlanPartId: Uuid
) : Entity<Uuid, Workout_entries> {
    override fun toDB(): Workout_entries {
        return Workout_entries(
            id = id,
            start_time = startTime,
            finish_time = finishTime,
            workout_plan_id = workoutPlanId,
            workout_plan_part_id = workoutPlanPartId
        )
    }

    companion object : EntityConvertable<Workout_entries, WorkoutEntry> {
        override fun fromDB(db: Workout_entries): WorkoutEntry {
            return WorkoutEntry(
                id = db.id,
                startTime = db.start_time,
                finishTime = db.finish_time,
                workoutPlanId = db.workout_plan_id,
                workoutPlanPartId = db.workout_plan_part_id
            )
        }
    }
}
