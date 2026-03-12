package net.jim.components.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
@Polymorphic
sealed interface NavRoute : NavKey {

    @Serializable
    data object MainRoute : NavRoute

    @Serializable
    data class WorkoutPlanRoute(val workoutPlanId: Uuid?) : NavRoute

}