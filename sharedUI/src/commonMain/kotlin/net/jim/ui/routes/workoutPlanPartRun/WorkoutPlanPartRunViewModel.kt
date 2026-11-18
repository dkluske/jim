package net.jim.ui.routes.workoutPlanPartRun

import net.jim.Root
import net.jim.data.models.Identifier
import net.jim.ui.Route
import net.jim.ui.routes.util.ViewModel

data class WorkoutPlanPartRunViewModel(
    override val root: Root,
    override val routeOrigin: Route,
    val workoutPlanPartIdentifier: Identifier
) : ViewModel
