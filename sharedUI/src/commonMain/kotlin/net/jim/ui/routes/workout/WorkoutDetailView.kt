package net.jim.ui.routes.workout

import androidx.compose.runtime.Composable
import net.jim.ui.components.SecondaryRouteScaffold
import kotlin.uuid.Uuid

@Composable
fun WorkoutDetailView(
    workoutId: Uuid,
    viewModel: WorkoutDetailViewModel,
) {
    SecondaryRouteScaffold(
        root = viewModel.root,
        title = {}
    ) {

    }
}