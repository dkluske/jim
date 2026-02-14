package net.jim.data.models

import kotlinx.serialization.Serializable
import net.jim.sqldelight.Json_exercises
import kotlin.uuid.Uuid

@Serializable
data class PhysicalJsonExercise(
    override val id: Uuid,
    override val name: String,
    override val revision: Long = 0L,
    override val level: JsonExerciseType.LevelEnum,
    override val category: JsonExerciseType.CategoryEnum,
    val force: JsonExerciseType.ForceEnum? = null,
    val mechanic: JsonExerciseType.MechanicEnum? = null,
    val primaryMuscles: List<JsonExerciseType.MuscleEnum>,
    val secondaryMuscles: List<JsonExerciseType.MuscleEnum>,
    val instructions: List<String>
) : JsonExerciseType {
    override fun toDB(): Json_exercises {
        return Json_exercises(
            id = id,
            name = name,
            level = level,
            category = category,
            revision = revision,
            document = this
        )
    }

    companion object : EntityConvertable<Json_exercises, PhysicalJsonExercise> {
        override fun fromDB(db: Json_exercises): PhysicalJsonExercise {
            return db.document as? PhysicalJsonExercise
                ?: throw IllegalStateException("Queried document is not of type PhysicalJsonExercise")
        }
    }
}
