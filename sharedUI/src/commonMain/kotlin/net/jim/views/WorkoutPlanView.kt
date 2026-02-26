package net.jim.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jim.sharedui.generated.resources.Res
import jim.sharedui.generated.resources.newWorkoutPlan
import net.jim.components.utils.JimCard
import net.jim.data.models.WorkoutPlan
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.table.WorkoutPlanPartTable
import net.jim.data.table.WorkoutPlanTable
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.Uuid

data class WorkoutPlanViewModel(
    override val root: Root,
    val workoutId: Uuid?
): JimViewModel {
    fun loadWorkout(id: Uuid): WorkoutPlan {
        return WorkoutPlanTable.getById(id)
    }

    fun loadWorkoutPlanParts(workoutPlanId: Uuid): List<WorkoutPlanPart> {
        return WorkoutPlanPartTable.getByWorkoutPlanId(workoutPlanId)
    }
}

@Composable
fun WorkoutPlanView(
    vm: WorkoutPlanViewModel
) {
    var editWorkoutName by remember { mutableStateOf(false) }
    var workout by remember { mutableStateOf<WorkoutPlan?>(null) }
    val planParts = remember { mutableStateListOf<WorkoutPlanPart>() }

    LaunchedEffect(Unit) {
        vm.workoutId?.let {
            workout = vm.loadWorkout(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        /**
         * Heading
         */
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (editWorkoutName) {
                TextField(
                    value = workout?.name ?: stringResource(Res.string.newWorkoutPlan),
                    onValueChange = {
                        workout = workout ?: WorkoutPlan(
                            id = Uuid.random(),
                            name = it,
                            default = false
                        )
                    }
                )
            } else {
                Text(
                    text = workout?.name ?: stringResource(Res.string.newWorkoutPlan),
                )
            }
            IconButton(
                onClick = {
                    editWorkoutName = !editWorkoutName
                }
            ) {
                Icon(
                    imageVector = if (editWorkoutName) {
                        Icons.Filled.Edit
                    } else {
                        Icons.Filled.Done
                    },
                    contentDescription = "rename workout plan"
                )
            }
        }

        /**
         * Workout Plan Parts
         */
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            JimCard {
                val lazyListState = rememberLazyListState()
                LazyColumn(
                    state = lazyListState
                ) {
                    items(planParts.size) { index ->

                    }
                }
            }
        }
    }
}