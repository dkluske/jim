package net.jim.data.table

import net.jim.data.models.JsonExerciseType
import net.jim.data.models.PhysicalJsonExercise
import net.jim.sqldelight.JimRuntimeDatabase
import kotlin.uuid.Uuid

data class JsonExerciseTable(
    override val database: JimRuntimeDatabase
) : Table<Uuid, JsonExerciseType> {
    // TODO: add mapping for JsonExerciseType Interface instead of straight PhysicalJsonExercise
    val MAX_REVISION: Int = 0

    override fun getById(id: Uuid): PhysicalJsonExercise {
        return PhysicalJsonExercise.fromDB(
            database.jsonExercisesQueries.getById(id = id).executeAsOne()
        )
    }

    override fun getAll(): List<JsonExerciseType> {
        return database.jsonExercisesQueries.getAll().executeAsList().map {
            PhysicalJsonExercise.fromDB(it)
        }
    }

    override fun save(entity: JsonExerciseType): JsonExerciseType {
        database.jsonExercisesQueries.insert(
            json_exercises = entity.toDB()
        )

        return entity
    }

    override fun delete(id: Uuid) {
        database.jsonExercisesQueries.deleteById(id = id)
    }

    override fun update(
        id: Uuid,
        entity: JsonExerciseType
    ): JsonExerciseType {
        database.jsonExercisesQueries.updateById(
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
        return database.jsonExercisesQueries.getMaxRevision().executeAsOne().MAX ?: -1L
    }

    fun searchByNamePaged(
        name: String,
        pageSize: Int,
        offset: Int,
    ): List<JsonExerciseType> {
        return database.jsonExercisesQueries.searchByNamePaged(
            name = name,
            limit = pageSize.toLong(),
            offset = offset.toLong()
        ).executeAsList().map {
            PhysicalJsonExercise.fromDB(it)
        }
    }
}