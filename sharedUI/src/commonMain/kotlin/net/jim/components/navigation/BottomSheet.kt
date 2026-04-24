package net.jim.components.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import net.jim.data.models.WorkoutPlanPart

sealed interface BottomSheet : NavRoute, NavKey {
    @Serializable
    data class JimWorkoutPlanPartModalBottomSheet(
        val workoutPlanPart: WorkoutPlanPart?,
    ) : BottomSheet, NavKey
}