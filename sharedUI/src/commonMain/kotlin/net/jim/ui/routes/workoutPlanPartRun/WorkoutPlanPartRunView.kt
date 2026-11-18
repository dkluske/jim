package net.jim.ui.routes.workoutPlanPartRun

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.jim.ui.components.SecondaryRouteScaffold

@Composable
fun WorkoutPlanPartRunView(
    viewModel: WorkoutPlanPartRunViewModel
) {
    SecondaryRouteScaffold(
        viewModel = viewModel,
        title = { Text(viewModel.workoutPlanPartIdentifier.name) }
    ) {

    }
}