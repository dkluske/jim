package net.jim.ui.routes.workoutPlanPartRun

import androidx.compose.runtime.Composable
import net.jim.ui.components.SecondaryRouteScaffold

@Composable
fun WorkoutPlanPartRunView(
    viewModel: WorkoutPlanPartRunViewModel
) {
    SecondaryRouteScaffold(
        root = viewModel.root,
        title = {}
    ) {

    }
}