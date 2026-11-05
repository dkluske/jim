package net.jim.ui.routes.workoutPlan

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jim.components.EditableHeader
import net.jim.components.WorkoutPlanSummaryWidget
import net.jim.data.models.Identifier
import net.jim.data.models.WorkoutPlan
import net.jim.data.models.WorkoutPlanPart
import net.jim.ui.WorkoutPlanPartRoute
import net.jim.ui.components.SecondaryRouteScaffold
import kotlin.uuid.Uuid

class WorkoutPlanFactory {
    private var name: String = ""
    private var default: Boolean = false

    private var id: Uuid = Uuid.random()

    private val parts: MutableList<WorkoutPlanPart> = mutableListOf()

    fun id(id: Uuid): WorkoutPlanFactory {
        this.id = id
        return this
    }

    fun id(): Uuid {
        return id
    }

    fun name(name: String): WorkoutPlanFactory {
        this.name = name
        return this
    }

    fun name(): String {
        return this.name
    }

    fun default(default: Boolean): WorkoutPlanFactory {
        this.default = default
        return this
    }

    fun default(): Boolean {
        return this.default
    }

    fun parts(parts: List<WorkoutPlanPart>): WorkoutPlanFactory {
        this.parts.clear()
        this.parts.addAll(parts)
        return this
    }

    fun parts(): List<WorkoutPlanPart> {
        return this.parts
    }

    fun build(): WorkoutPlan {
        return WorkoutPlan(
            id = id,
            name = name,
            default = default,
        )
    }

    companion object {
        fun fromWorkoutPlan(plan: WorkoutPlan, parts: List<WorkoutPlanPart>): WorkoutPlanFactory {
            return WorkoutPlanFactory()
                .id(plan.id)
                .name(plan.name)
                .default(plan.default)
                .parts(parts)
        }
    }
}

@Composable
fun WorkoutPlanView(
    workoutPlanIdentifier: Identifier?,
    viewModel: WorkoutPlanViewModel
) {
    val workoutPlan = remember {
        mutableStateOf(WorkoutPlanFactory().name(workoutPlanIdentifier?.name ?: "New Plan")) // TODO: i18n
    }

    LaunchedEffect(Unit) {
        workoutPlanIdentifier?.let {
            workoutPlan.value = WorkoutPlanFactory.fromWorkoutPlan(
                plan = viewModel.getWorkoutPlanById(it.id),
                parts = viewModel.getPlanPartsByWorkoutPlanId(it.id)
            )
        }
    }

    SecondaryRouteScaffold(
        root = viewModel.root,
        title = {
            EditableHeader(
                value = workoutPlan.value.name(),
                onValueChange = {
                    workoutPlan.value = workoutPlan.value.name(it)
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        val planToSave = workoutPlan.value.build()
                        val parts = workoutPlan.value.parts()

                        viewModel.saveWorkoutPlan(planToSave)
                        viewModel.saveWorkoutPlanParts(parts)

                        viewModel.root.navStack.value.removeLastOrNull()
                    }
                ) {
                    Text(
                        text = "Save" // TODO: i18n
                    )
                }
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            Text(
                text = "Summary" // TODO: i18n
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            WorkoutPlanSummaryWidget(
                parts = workoutPlan.value.parts()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            Text(
                text = "Parts" // TODO: i18n
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                workoutPlan.value.parts().forEach {
                    // Custom ListItem
                }
                // Add Part Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            viewModel.root.navStack.value += WorkoutPlanPartRoute
                        }
                    ) {
                        Text(
                            text = "Add Part"
                        )
                    }
                }
            }
        }
    }
}