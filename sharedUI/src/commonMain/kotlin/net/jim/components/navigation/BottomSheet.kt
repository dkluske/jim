package net.jim.components.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import net.jim.data.models.WorkoutPlanPart
import kotlin.uuid.Uuid

sealed interface BottomSheet : NavRoute, NavKey {
    @Serializable
    data class JimWorkoutPlanPartModalBottomSheet(
        val workoutPlanId: Uuid,
        val workoutPlanPart: WorkoutPlanPart?,
        val onConfirm: (WorkoutPlanPart) -> Unit
    ) : BottomSheet, NavKey
}