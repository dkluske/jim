package net.jim.data.table

import net.jim.data.models.JsonExercise
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference
import kotlin.uuid.Uuid

object JsonExerciseTable : Table<Uuid, JsonExercise> {
    const val MAX_REVISION: Int = 0

    override val database: AtomicReference<JimRuntimeDatabase?> = AtomicReference(null)

    override fun getById(id: Uuid): JsonExercise {
        return JsonExercise.fromDB(
            getDatabase().jsonExercisesQueries.getById(id = id).executeAsOne()
        )
    }

    override fun getAll(): List<JsonExercise> {
        return getDatabase().jsonExercisesQueries.getAll().executeAsList().map {
            JsonExercise.fromDB(it)
        }
    }

    override fun save(entity: JsonExercise): JsonExercise {
        getDatabase().jsonExercisesQueries.insert(
            json_exercises = entity.toDB()
        )

        return entity
    }

    override fun delete(id: Uuid) {
        getDatabase().jsonExercisesQueries.deleteById(id = id)
    }

    override fun update(
        id: Uuid,
        entity: JsonExercise
    ): JsonExercise {
        getDatabase().jsonExercisesQueries.updateById(
            id = id,
            name = entity.name,
            force = entity.force,
            level = entity.level,
            mechanic = entity.mechanic,
            primary_muscles = entity.primaryMuscles,
            secondary_muscles = entity.secondaryMuscles,
            instructions = entity.instructions,
            category = entity.category,
            id_ = id,
            revision = entity.revision
        )

        return entity
    }

    fun getMaxStoredRevision(): Long {
        return getDatabase().jsonExercisesQueries.getMaxRevision().executeAsOne().MAX ?: -1L
    }
}