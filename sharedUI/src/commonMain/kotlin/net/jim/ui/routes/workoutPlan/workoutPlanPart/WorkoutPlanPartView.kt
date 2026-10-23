package net.jim.ui.routes.workoutPlan.workoutPlanPart

import androidx.compose.runtime.Composable
import net.jim.ui.components.SecondaryRouteScaffold

@Composable
fun WorkoutPlanPartView(
    viewModel: WorkoutPlanPartViewModel
) {
    SecondaryRouteScaffold(
        root = viewModel.root,
        title = {}
    ) {

    }
}