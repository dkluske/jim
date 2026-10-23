package net.jim.data.table

import net.jim.data.models.WorkoutPlan
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.uuid.Uuid

data class WorkoutPlanTable(
    override val database: JimRuntimeDatabase
) : Table<Uuid, WorkoutPlan> {
    override fun getById(id: Uuid): WorkoutPlan {
        return database.workoutPlansQueries.getAll().executeAsOne().let {
            WorkoutPlan.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutPlan> {
        return database.workoutPlansQueries.getAll().executeAsList().map {
            WorkoutPlan.fromDB(it)
        }
    }

    override fun save(entity: WorkoutPlan): WorkoutPlan {
        database.workoutPlansQueries.insert(
            entity.toDB()
        )
        return entity
    }

    override fun delete(id: Uuid) {
        database.workoutPlansQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutPlan
    ): WorkoutPlan {
        database.workoutPlansQueries.updateById(
            id = id,
            name = entity.name,
            default_plan = entity.default,
            id_ = id
        )
        return entity
    }

    fun getDefaultPlan(): WorkoutPlan {
        return database.workoutPlansQueries.getDefault().executeAsOne().let {
            WorkoutPlan.fromDB(it)
        }
    }
}