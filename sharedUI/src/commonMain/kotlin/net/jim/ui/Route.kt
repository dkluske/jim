package net.jim.ui

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
sealed interface Route : NavKey {
    @Serializable
    @Polymorphic
    sealed interface MainRoute : Route, NavKey

    @Serializable
    @Polymorphic
    sealed interface SecondaryRoute : Route, NavKey
}

sealed interface BottomSheet : Route, NavKey {
    @Serializable
    data object WorkoutPlanPartSelectionModal : BottomSheet

    @Serializable
    data object JsonExerciseSearchModal : BottomSheet
}

sealed interface Dialog : Route, NavKey {
    @Serializable
    data object ResetAppDialog : Dialog
}

@Serializable
data object HomeRoute : Route.MainRoute

@Serializable
data object SettingsRoute : Route.MainRoute

@Serializable
data object StatsRoute : Route.MainRoute

@Serializable
data object WorkoutPlanPartRunRoute : Route.SecondaryRoute

@Serializable
data object WorkoutPlanRoute : Route.SecondaryRoute

@Serializable
data object WorkoutPlanDetailRoute : Route.SecondaryRoute