package net.jim.data.table

import net.jim.data.models.WorkoutEntryExercise
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference
import kotlin.uuid.Uuid

object WorkoutEntryExerciseTable : Table<Uuid, WorkoutEntryExercise> {
    override val database: AtomicReference<JimRuntimeDatabase?> = AtomicReference(null)

    override fun getById(id: Uuid): WorkoutEntryExercise {
        return getDatabase().workoutEntryExercisesQueries.getById(id).executeAsOne().let {
            WorkoutEntryExercise.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutEntryExercise> {
        return getDatabase().workoutEntryExercisesQueries.getAll().executeAsList().map {
            WorkoutEntryExercise.fromDB(it)
        }
    }

    override fun save(entity: WorkoutEntryExercise): WorkoutEntryExercise {
        getDatabase().workoutEntryExercisesQueries.insert(entity.toDB())
        return entity
    }

    override fun delete(id: Uuid) {
        getDatabase().workoutEntryExercisesQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutEntryExercise
    ): WorkoutEntryExercise {
        getDatabase().workoutEntryExercisesQueries.updateById(
            id = id,
            workout_plan_part_id = entity.workoutPlanPartId,
            workout_entry_id = entity.workoutEntryId,
            execution = entity.execution,
            id_ = id
        )
        return entity
    }
}