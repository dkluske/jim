package net.jim.data.table

import net.jim.data.models.WorkoutPlanPart
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference
import kotlin.uuid.Uuid

object WorkoutPlanPartTable : Table<Uuid, WorkoutPlanPart> {
    override val database: AtomicReference<JimRuntimeDatabase?> = AtomicReference(null)

    override fun getById(id: Uuid): WorkoutPlanPart {
        return getDatabase().workoutPlanPartsQueries.getById(id).executeAsOne().let {
            WorkoutPlanPart.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutPlanPart> {
        return getDatabase().workoutPlanPartsQueries.getAll().executeAsList().map {
            WorkoutPlanPart.fromDB(it)
        }
    }

    fun getByWorkoutPlanId(workoutPlanId: Uuid): List<WorkoutPlanPart> {
        return getDatabase().workoutPlanPartsQueries.getByWorkoutPlanId(workout_plan_id = workoutPlanId).executeAsList().map {
            WorkoutPlanPart.fromDB(it)
        }
    }

    override fun save(entity: WorkoutPlanPart): WorkoutPlanPart {
        getDatabase().workoutPlanPartsQueries.insert(
            entity.toDB()
        )
        return entity
    }

    override fun delete(id: Uuid) {
        getDatabase().workoutPlanPartsQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutPlanPart
    ): WorkoutPlanPart {
        getDatabase().workoutPlanPartsQueries.updateById(
            id = id,
            name = entity.name,
            workout_plan_id = entity.workoutPlanId,
            day_of_week = entity.dayOfWeek,
            exercises = entity.exercises,
            id_ = id
        )
        return entity
    }
}