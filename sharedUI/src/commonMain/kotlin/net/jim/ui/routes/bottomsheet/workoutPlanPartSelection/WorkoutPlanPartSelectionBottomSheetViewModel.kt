package net.jim.ui.routes.bottomsheet.workoutPlanPartSelection

import net.jim.Root
import net.jim.data.models.Identifier
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.table.WorkoutPlanPartTable
import net.jim.ui.Route
import net.jim.ui.routes.util.ViewModel

data class WorkoutPlanPartSelectionBottomSheetViewModel(
    override val root: Root,
    override val routeOrigin: Route,
    val workoutPlanIdentifier: Identifier,
    private val workoutPlanPartTable: WorkoutPlanPartTable,
) : ViewModel {
    fun getPlanParts(): List<WorkoutPlanPart> {
        return workoutPlanPartTable.getByWorkoutPlanId(workoutPlanId = workoutPlanIdentifier.id)
    }
}
