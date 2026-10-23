package net.jim.data.table

import net.jim.data.models.WorkoutEntry
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class WorkoutEntryTable(
    override val database: JimRuntimeDatabase
) : Table<Uuid, WorkoutEntry> {
    override fun getById(id: Uuid): WorkoutEntry {
        return database.workoutEntriesQueries.getById(id).executeAsOne().let {
            WorkoutEntry.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutEntry> {
        return database.workoutEntriesQueries.getAll().executeAsList().map {
            WorkoutEntry.fromDB(it)
        }
    }

    override fun save(entity: WorkoutEntry): WorkoutEntry {
        database.workoutEntriesQueries.insert(entity.toDB())
        return entity
    }

    override fun delete(id: Uuid) {
        database.workoutEntriesQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutEntry
    ): WorkoutEntry {
        database.workoutEntriesQueries.updateById(
            id = id,
            start_time = entity.startTime,
            finish_time = entity.finishTime,
            workout_plan_id = entity.workoutPlanId,
            workout_plan_part_id = entity.workoutPlanPartId,
            id_ = id
        )
        return entity
    }

    fun getAllAfter(date: Instant): List<WorkoutEntry> {
        return database.workoutEntriesQueries.getAllAfter(date).executeAsList().map {
            WorkoutEntry(
                id = it.id,
                startTime = it.start_time,
                finishTime = it.finish_time,
                workoutPlanId = it.workout_plan_id,
                workoutPlanPartId = it.workout_plan_part_id
            )
        }
    }
}