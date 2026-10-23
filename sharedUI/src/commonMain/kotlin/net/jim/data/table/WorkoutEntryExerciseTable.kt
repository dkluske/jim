package net.jim.data.table

import net.jim.data.models.WorkoutEntryExercise
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.uuid.Uuid

data class WorkoutEntryExerciseTable(
    override val database: JimRuntimeDatabase
) : Table<Uuid, WorkoutEntryExercise> {

    override fun getById(id: Uuid): WorkoutEntryExercise {
        return database.workoutEntryExercisesQueries.getById(id).executeAsOne().let {
            WorkoutEntryExercise.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutEntryExercise> {
        return database.workoutEntryExercisesQueries.getAll().executeAsList().map {
            WorkoutEntryExercise.fromDB(it)
        }
    }

    override fun save(entity: WorkoutEntryExercise): WorkoutEntryExercise {
        database.workoutEntryExercisesQueries.insert(entity.toDB())
        return entity
    }

    override fun delete(id: Uuid) {
        database.workoutEntryExercisesQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutEntryExercise
    ): WorkoutEntryExercise {
        database.workoutEntryExercisesQueries.updateById(
            id = id,
            workout_plan_part_id = entity.workoutPlanPartId,
            workout_entry_id = entity.workoutEntryId,
            execution = entity.execution,
            id_ = id
        )
        return entity
    }
}