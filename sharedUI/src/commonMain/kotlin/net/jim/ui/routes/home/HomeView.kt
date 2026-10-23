package net.jim.ui.routes.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jim.components.CalendarWidget
import net.jim.components.WorkoutHistoryWidget
import net.jim.components.WorkoutPlansWidget
import net.jim.ui.BottomSheet
import net.jim.ui.components.MainRouteScaffold

@Composable
fun HomeView(
    viewModel: HomeViewModel
) {
    MainRouteScaffold(
        root = viewModel.root
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = "Ready to go?"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            CalendarWidget(
                dates = listOf() // TODO: fill data from viewModel
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = "Workout Plans"
            )
        }
        val workoutPlanScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .horizontalScroll(workoutPlanScrollState)
        ) {
            WorkoutPlansWidget(
                plans = listOf(), // TODO: fill with data from viewModel
                onClick = {
                    // TODO: navigate to plan edit
                },
                onStart = {
                    viewModel.root.navStack.value += BottomSheet.WorkoutPlanPartSelectionModal
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = "History"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            WorkoutHistoryWidget(
                workouts = listOf(), // TODO: fill with data from viewModel
                onClick = {
                    // TODO: navigate to workout detail view
                }
            )
        }
    }
}