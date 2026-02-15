package net.jim.data.table

import net.jim.data.models.WorkoutPlan
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference
import kotlin.uuid.Uuid

object WorkoutPlanTable : Table<Uuid, WorkoutPlan> {
    override val database: AtomicReference<JimRuntimeDatabase?> = AtomicReference(null)

    override fun getById(id: Uuid): WorkoutPlan {
        return getDatabase().workoutPlansQueries.getAll().executeAsOne().let {
            WorkoutPlan.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutPlan> {
        return getDatabase().workoutPlansQueries.getAll().executeAsList().map {
            WorkoutPlan.fromDB(it)
        }
    }

    override fun save(entity: WorkoutPlan): WorkoutPlan {
        getDatabase().workoutPlansQueries.insert(
            entity.toDB()
        )
        return entity
    }

    override fun delete(id: Uuid) {
        getDatabase().workoutPlansQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutPlan
    ): WorkoutPlan {
        getDatabase().workoutPlansQueries.updateById(
            id = id,
            name = entity.name,
            default_plan = entity.default,
            workout_plan_ids = entity.workoutPlanPartIds,
            id_ = id
        )
        return entity
    }
}