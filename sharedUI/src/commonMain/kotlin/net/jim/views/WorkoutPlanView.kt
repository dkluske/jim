package net.jim.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jim.sharedui.generated.resources.*
import net.jim.components.JimEditableHeader
import net.jim.components.utils.JimCard
import net.jim.data.models.WorkoutPlan
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.table.WorkoutPlanPartTable
import net.jim.data.table.WorkoutPlanTable
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.Uuid

data class WorkoutPlanViewModel(
    override val root: Root,
    val workoutId: Uuid?,
    val onOpenPlanPart: (WorkoutPlanPart?) -> Unit,
    val onNavigateBack: () -> Unit
): JimViewModel {
    fun loadWorkout(id: Uuid): WorkoutPlan {
        return WorkoutPlanTable.getById(id)
    }

    fun loadWorkoutPlanParts(workoutPlanId: Uuid): List<WorkoutPlanPart> {
        return WorkoutPlanPartTable.getByWorkoutPlanId(workoutPlanId)
    }

    fun saveWorkoutPlan(workoutPlan: WorkoutPlan, parts: List<WorkoutPlanPart>) {
        WorkoutPlanTable.save(workoutPlan)
        parts.forEach {
            WorkoutPlanPartTable.save(it)
        }
    }
}

@Composable
fun WorkoutPlanView(
    vm: WorkoutPlanViewModel
) {
    var workout by remember { mutableStateOf<WorkoutPlan?>(null) }
    val planParts = remember { mutableStateListOf<WorkoutPlanPart>() }

    LaunchedEffect(Unit) {
        vm.workoutId?.let {
            workout = vm.loadWorkout(it)
            planParts.clear()
            planParts.addAll(vm.loadWorkoutPlanParts(it))
        }
    }

    Scaffold(
        content = {
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
                    IconButton(
                        onClick = { vm.onNavigateBack() }
                    ) {
                        Text(
                            text = "<"
                        )
                    }
                    JimEditableHeader(
                        value = workout?.name ?: stringResource(Res.string.newWorkoutPlan),
                        onValueChange = {
                            workout = workout?.copy(name = it) ?: WorkoutPlan(
                                id = Uuid.random(),
                                name = it,
                                default = false
                            )
                        }
                    )
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
                                ListItem(
                                    headlineContent = {
                                        // Plan name
                                        Text(
                                            text = planParts[index].name
                                        )
                                    },
                                    trailingContent = {
                                        // Quantity Spinner
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            if (planParts[index].quantity == 1) {
                                                IconButton(
                                                    onClick = { planParts.removeAt(index) }
                                                ) {
                                                    Icon(Icons.Filled.Delete, "delete part")
                                                }
                                            } else {
                                                IconButton(
                                                    onClick = {
                                                        planParts[index] =
                                                            planParts[index].copy(quantity = planParts[index].quantity - 1)
                                                    }
                                                ) {
                                                    Text(
                                                        text = "-"
                                                    )
                                                }
                                            }
                                            Text(
                                                text = planParts[index].quantity.toString(),
                                            )
                                            IconButton(
                                                onClick = {
                                                    planParts[index] =
                                                        planParts[index].copy(quantity = planParts[index].quantity + 1)
                                                }
                                            ) {
                                                Text(
                                                    text = "+"
                                                )
                                            }
                                        }
                                    },
                                    supportingContent = {
                                        Button(
                                            onClick = {
                                                vm.onOpenPlanPart(planParts[index])
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(Res.string.edit)
                                            )
                                        }
                                    }
                                )
                            }
                            item {
                                Button(
                                    onClick = {
                                        vm.onOpenPlanPart(null)
                                    }
                                ) {
                                    Icon(Icons.Filled.AddCircle, "add workout plan part")
                                    Text(
                                        text = stringResource(Res.string.addWorkoutPlanPart)
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                    ) {
                        Button(
                            onClick = { workout?.let { vm.saveWorkoutPlan(workoutPlan = it, parts = planParts) } },
                        ) {
                            Icon(Icons.Filled.Done, "save plan")
                            Text(
                                text = stringResource(Res.string.save)
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    ).clickable {
                        // TODO: save workout plan logic
                    }, contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "save workout plan",
                            modifier = Modifier.padding(end = 4.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = stringResource(Res.string.save),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    )
}