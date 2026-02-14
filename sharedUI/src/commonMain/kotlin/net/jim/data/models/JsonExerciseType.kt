package net.jim.data.models

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.jim.data.models.serializer.*
import net.jim.sqldelight.Json_exercises
import kotlin.uuid.Uuid

@Serializable
@Polymorphic
@SerialName("JsonExerciseType")
sealed interface JsonExerciseType : Entity<Uuid, Json_exercises> {
    val name: String
    val revision: Long
    val level: LevelEnum
    val category: CategoryEnum

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
}