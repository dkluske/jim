package net.jim.data.table

import net.jim.data.models.WorkoutEntry
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference
import kotlin.time.Instant
import kotlin.uuid.Uuid

object WorkoutEntryTable : Table<Uuid, WorkoutEntry> {
    override val database: AtomicReference<JimRuntimeDatabase?> = AtomicReference(null)

    override fun getById(id: Uuid): WorkoutEntry {
        return getDatabase().workoutEntriesQueries.getById(id).executeAsOne().let {
            WorkoutEntry.fromDB(it)
        }
    }

    override fun getAll(): List<WorkoutEntry> {
        return getDatabase().workoutEntriesQueries.getAll().executeAsList().map {
            WorkoutEntry.fromDB(it)
        }
    }

    override fun save(entity: WorkoutEntry): WorkoutEntry {
        getDatabase().workoutEntriesQueries.insert(entity.toDB())
        return entity
    }

    override fun delete(id: Uuid) {
        getDatabase().workoutEntriesQueries.deleteById(id)
    }

    override fun update(
        id: Uuid,
        entity: WorkoutEntry
    ): WorkoutEntry {
        getDatabase().workoutEntriesQueries.updateById(
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
        return getDatabase().workoutEntriesQueries.getAllAfter(date).executeAsList().map {
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