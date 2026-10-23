package net.jim.ui.routes.workoutPlan

import androidx.compose.runtime.Composable
import net.jim.ui.components.SecondaryRouteScaffold

@Composable
fun WorkoutPlanView(
    viewModel: WorkoutPlanViewModel
) {
    SecondaryRouteScaffold(
        root = viewModel.root,
        title = {}
    ) {

    }
}