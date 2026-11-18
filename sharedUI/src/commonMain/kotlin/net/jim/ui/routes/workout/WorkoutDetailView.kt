package net.jim.ui.routes.workout

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.jim.ui.components.SecondaryRouteScaffold

@Composable
fun WorkoutEntryDetailView(
    viewModel: WorkoutEntryDetailViewModel,
) {
    SecondaryRouteScaffold(
        viewModel = viewModel,
        title = {
            Text(
                text = viewModel.workoutIdentifier.name
            )
        }
    ) {

    }
}