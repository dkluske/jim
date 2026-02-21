package net.jim.views

import androidx.compose.runtime.Composable
import net.jim.data.models.WorkoutPlan
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.table.WorkoutPlanPartTable
import net.jim.data.table.WorkoutPlanTable
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

}