package net.jim.data.models

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.uuid.Uuid

@Serializable
@SerialName("WorkoutPlanExercise")
data class WorkoutPlanExercise(
    val id: Uuid,
    val index: Int,
    val jsonExerciseId: Uuid,
    val sets: Int,
    val repetitionInterval: RepetitionInterval
) {
    @Serializable
    @Polymorphic
    @SerialName("RepetitionInterval")
    sealed interface RepetitionInterval

    @Serializable
    @SerialName("RepetitionInterval.Timed")
    data class Timed(
        val duration: Duration
    ) : RepetitionInterval

    @Serializable
    @SerialName("RepetitionInterval.Repeating")
    data class Repeating(
        val repetitions: Int
    )
}
