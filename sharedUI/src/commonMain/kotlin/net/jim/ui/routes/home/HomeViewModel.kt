package net.jim.ui.routes.home

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.jim.Root
import net.jim.components.CalendarWidgetEntry
import net.jim.components.WorkoutHistoryWidget
import net.jim.components.WorkoutWidgetPlan
import net.jim.data.table.WorkoutEntryTable
import net.jim.data.table.WorkoutPlanTable
import net.jim.ui.routes.util.ViewModel
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

data class HomeViewModel(
    override val root: Root,
    private val workoutEntryTable: WorkoutEntryTable,
    private val workoutPlanTable: WorkoutPlanTable,
) : ViewModel {
    fun getCalendarWorkoutEntries(): List<CalendarWidgetEntry> {
        val today = Clock.System.now()
        val start = today.minus(6.days)

        val lastWorkouts = workoutEntryTable.getAllAfter(start)

        return (0..6).map { day ->
            CalendarWidgetEntry(
                date = start.plus(day.days),
                hasWorkedOut = lastWorkouts.any {
                    it.finishTime?.toLocalDateTime(TimeZone.currentSystemDefault())?.date == today.toLocalDateTime(
                        TimeZone.currentSystemDefault()
                    ).date
                }
            )
        }
    }

    fun getWorkoutPlans(): List<WorkoutWidgetPlan> {
        return workoutPlanTable.getAll().map { workoutPlan ->
            WorkoutWidgetPlan(
                id = workoutPlan.id,
                name = workoutPlan.name,
                isDefault = workoutPlan.default,
                primaryMuscles = listOf() //TODO: get info from json exercises
            )
        }
    }

    fun getLastWorkouts(): List<WorkoutHistoryWidget> {
        return workoutEntryTable.getLast(count = 3).map {
            WorkoutHistoryWidget(
                id = it.id,
                finishDate = it.finishTime!!
            )
        }
    }
}
