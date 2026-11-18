package net.jim.ui.routes.workout

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.jim.ui.components.SecondaryRouteScaffold

@Composable
fun WorkoutEntryListView(
    viewModel: WorkoutEntryListViewModel
) {
    SecondaryRouteScaffold(
        viewModel = viewModel,
        title = {
            Text("Workout History") // TODO: i18n
        }
    ) {

    }
}