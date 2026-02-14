package net.jim.data.table

import net.jim.data.models.JsonExerciseType
import net.jim.data.models.PhysicalJsonExercise
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.concurrent.atomics.AtomicReference
import kotlin.uuid.Uuid

object JsonExerciseTable : Table<Uuid, JsonExerciseType> {
    const val MAX_REVISION: Int = 0

    override val database: AtomicReference<JimRuntimeDatabase?> = AtomicReference(null)

    override fun getById(id: Uuid): PhysicalJsonExercise {
        return PhysicalJsonExercise.fromDB(
            getDatabase().jsonExercisesQueries.getById(id = id).executeAsOne()
        )
    }

    override fun getAll(): List<JsonExerciseType> {
        return getDatabase().jsonExercisesQueries.getAll().executeAsList().map {
            PhysicalJsonExercise.fromDB(it)
        }
    }

    override fun save(entity: JsonExerciseType): JsonExerciseType {
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
        entity: JsonExerciseType
    ): JsonExerciseType {
        getDatabase().jsonExercisesQueries.updateById(
            id = id,
            name = entity.name,
            level = entity.level,
            category = entity.category,
            id_ = id,
            revision = entity.revision,
            document = entity
        )

        return entity
    }

    fun getMaxStoredRevision(): Long {
        return getDatabase().jsonExercisesQueries.getMaxRevision().executeAsOne().MAX ?: -1L
    }
}