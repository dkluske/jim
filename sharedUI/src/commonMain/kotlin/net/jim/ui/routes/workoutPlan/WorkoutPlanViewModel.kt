package net.jim.ui.routes.workoutPlan

import net.jim.Root
import net.jim.data.models.WorkoutPlan
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.table.WorkoutPlanPartTable
import net.jim.data.table.WorkoutPlanTable
import net.jim.ui.routes.util.ViewModel
import kotlin.uuid.Uuid

data class WorkoutPlanViewModel(
    override val root: Root,
    private val workoutPlanTable: WorkoutPlanTable,
    private val workoutPlanPartTable: WorkoutPlanPartTable,
) : ViewModel {
    fun getWorkoutPlanById(id: Uuid): WorkoutPlan {
        return workoutPlanTable.getById(id)
    }

    fun getPlanPartsByWorkoutPlanId(id: Uuid): List<WorkoutPlanPart> {
        return workoutPlanPartTable.getByWorkoutPlanId(id)
    }

    fun saveWorkoutPlan(plan: WorkoutPlan) {
        workoutPlanTable.save(plan)
    }

    fun saveWorkoutPlanParts(parts: List<WorkoutPlanPart>) {
        parts.forEach {
            workoutPlanPartTable.save(it)
        }
    }
}
