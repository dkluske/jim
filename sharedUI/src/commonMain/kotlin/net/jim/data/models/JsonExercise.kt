package net.jim.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.jim.data.models.serializer.*
import net.jim.sqldelight.Json_exercises
import kotlin.uuid.Uuid

@Serializable
data class JsonExercise(
    override val id: Uuid,
    val name: String,
    val force: ForceEnum? = null,
    val level: LevelEnum,
    val mechanic: MechanicEnum? = null,
    val primaryMuscles: List<MuscleEnum>,
    val secondaryMuscles: List<MuscleEnum>,
    val instructions: List<String>,
    val category: CategoryEnum
) : Entity<Uuid, Json_exercises> {
    override fun toDB(): Json_exercises {
        return Json_exercises(
            id = id,
            name = name,
            force = force,
            level = level,
            mechanic = mechanic,
            primary_muscles = primaryMuscles,
            secondary_muscles = secondaryMuscles,
            instructions = instructions,
            category = category
        )
    }

    @Serializable(with = JsonExerciseForceSerializer::class)
    @SerialName("JsonExercise.ForceEnum")
    enum class ForceEnum {
        PUSH,
        PULL,
        STATIC
    }

    @Serializable(with = JsonExerciseLevelSerializer::class)
    @SerialName("JsonExercise.LevelEnum")
    enum class LevelEnum {
        BEGINNER,
        INTERMEDIATE,
        EXPERT
    }

    @Serializable(with = JsonExerciseMechanicSerializer::class)
    @SerialName("JsonExercise.MechanicEnum")
    enum class MechanicEnum {
        COMPOUND,
        ISOLATION
    }

    @Serializable(with = JsonExerciseMuscleSerializer::class)
    @SerialName("JsonExercise.MuscleEnum")
    enum class MuscleEnum {
        ABDOMINALS,
        HAMSTRINGS,
        CALVES,
        SHOULDERS,
        ADDUCTORS,
        GLUTES,
        QUADRICEPS,
        BICEPS,
        FOREARMS,
        ABDUCTORS,
        TRICEPS,
        CHEST,
        LOWER_BACK,
        TRAPS,
        MIDDLE_BACK,
        LATS,
        NECK
    }

    @Serializable(with = JsonExerciseCategorySerializer::class)
    @SerialName("JsonExercise.CategoryEnum")
    enum class CategoryEnum {
        STRENGTH,
        STRETCHING,
        PLYOMETRICS,
        STRONGMAN,
        POWERLIFTING,
        CARDIO,
        OLYMPIC_WEIGHTLIFTING,
        CROSSFIT,
        WEIGHTED_BODYWEIGHT,
        ASSISTED_BODYWEIGHT,
    }

    companion object : EntityConvertable<Json_exercises, JsonExercise> {
        override fun fromDB(db: Json_exercises): JsonExercise {
            return JsonExercise(
                id = db.id,
                name = db.name,
                force = db.force,
                level = db.level,
                mechanic = db.mechanic,
                primaryMuscles = db.primary_muscles,
                secondaryMuscles = db.secondary_muscles,
                instructions = db.instructions,
                category = db.category
            )
        }
    }
}
