package net.jim.data.table

import net.jim.data.models.JsonExercise
import kotlin.uuid.Uuid

object JsonExerciseTable : Table<Uuid, JsonExercise> {
    override val name = "json_exercises"

    override fun getById(id: Uuid): JsonExercise {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<JsonExercise> {
        TODO("Not yet implemented")
    }

    override fun save(entity: JsonExercise): JsonExercise {
        TODO("Not yet implemented")
    }

    override fun delete(id: Uuid) {
        TODO("Not yet implemented")
    }

    override fun update(
        id: Uuid,
        entity: JsonExercise
    ): JsonExercise {
        TODO("Not yet implemented")
    }
}