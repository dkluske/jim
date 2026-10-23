package net.jim.data.table

import net.jim.data.models.WorkoutPlanPart
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.uuid.Uuid

data class WorkoutPlanPartTable(
    override val database: JimRuntimeDatabase
) : Table<Uuid, WorkoutPlanPart> {
    override fun getById(id: Uuid): WorkoutPlanPart {
        return database.workoutPlanPartsQueries.getById(id).executeAsOne().let {
            WorkoutPlanPart.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutPlanPart> {
        return database.workoutPlanPartsQueries.getAll().executeAsList().map {
            WorkoutPlanPart.fromDB(it)
        }
    }

    fun getByWorkoutPlanId(workoutPlanId: Uuid): List<WorkoutPlanPart> {
        return database.workoutPlanPartsQueries.getByWorkoutPlanId(workout_plan_id = workoutPlanId).executeAsList()
            .map {
            WorkoutPlanPart.fromDB(it)
        }
    }

    override fun save(entity: WorkoutPlanPart): WorkoutPlanPart {
        database.workoutPlanPartsQueries.insert(
            entity.toDB()
        )
        return entity
    }

    override fun delete(id: Uuid) {
        database.workoutPlanPartsQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutPlanPart
    ): WorkoutPlanPart {
        database.workoutPlanPartsQueries.updateById(
            id = id,
            name = entity.name,
            workout_plan_id = entity.workoutPlanId,
            exercises = entity.exercises,
            id_ = id,
            quantity = entity.quantity.toLong()
        )
        return entity
    }
}