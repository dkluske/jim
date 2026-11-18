package net.jim.ui.routes.bottomsheet.workoutPlanPartSelection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jim.components.ListItem
import net.jim.data.models.Identifier
import net.jim.data.models.WorkoutPlanPart
import net.jim.ui.WorkoutPlanPartRunRoute

@Composable
fun WorkoutPlanPartSelectionBottomSheet(
    viewModel: WorkoutPlanPartSelectionBottomSheetViewModel
) {
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.root.navStack.value.remove(viewModel.routeOrigin)
        }
    ) {
        val parts = remember { mutableStateListOf<WorkoutPlanPart>() }
        LaunchedEffect(Unit) {
            parts.clear()
            parts.addAll(viewModel.getPlanParts())
        }

        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text(viewModel.workoutPlanIdentifier.name)
            Row(modifier = Modifier.fillMaxWidth()) {
                if (parts.isNotEmpty()) {
                    LazyColumn {
                        items(parts.size) { index ->
                            val part = parts[index]
                            ListItem(
                                onClick = {
                                    viewModel.root.navStack.value += WorkoutPlanPartRunRoute(
                                        workoutPlanPartIdentifier = part.let {
                                            Identifier(it.id, it.name)
                                        }
                                    )
                                    viewModel.root.navStack.value.remove(viewModel.routeOrigin)
                                }
                            ) {
                                Text(part.name)
                            }
                        }
                    }
                } else {
                    Column {
                        ListItem {
                            Text(
                                text = "There are no parts to choose from currently.",
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    }
                }
            }
        }


    }
}