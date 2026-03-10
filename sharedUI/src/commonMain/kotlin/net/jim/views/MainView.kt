package net.jim.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jim.sharedui.generated.resources.Res
import jim.sharedui.generated.resources.latestWorkouts
import jim.sharedui.generated.resources.readyToWorkQuestion
import jim.sharedui.generated.resources.yourPlans
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime
import net.jim.components.*
import net.jim.data.models.JsonExerciseType
import net.jim.data.models.WorkoutPlan
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.table.WorkoutEntryTable
import net.jim.data.table.WorkoutPlanPartTable
import net.jim.data.table.WorkoutPlanTable
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class MainViewModel(
    override val root: Root
) : JimViewModel {
    fun getWorkoutPlans(): List<JimWorkoutWidgetPlan> {
        return WorkoutPlanTable.getAll().map {
            JimWorkoutWidgetPlan(
                id = it.id,
                name = it.name,
                isDefault = it.default,
                primaryMuscles = listOf() // TODO: resolve
            )
        }.ifEmpty {
            listOf( // TODO: only mockup data for testing the layout
                JimWorkoutWidgetPlan(
                    id = Uuid.random(),
                    name = "Full Body",
                    isDefault = true,
                    primaryMuscles = listOf(
                        JsonExerciseType.MuscleEnum.BICEPS,
                        JsonExerciseType.MuscleEnum.CHEST,
                        JsonExerciseType.MuscleEnum.ABDOMINALS
                    )
                ),
                JimWorkoutWidgetPlan(
                    id = Uuid.random(),
                    name = "Cardio",
                    isDefault = false,
                    primaryMuscles = listOf(
                        JsonExerciseType.MuscleEnum.HAMSTRINGS,
                    )
                )
            )
        }
    }

    fun getDefaultWorkoutPlan(): WorkoutPlan {
        return WorkoutPlanTable.getDefaultPlan()
    }

    fun getPartsForPlan(planId: Uuid): List<WorkoutPlanPart> {
        return WorkoutPlanPartTable.getByWorkoutPlanId(workoutPlanId = planId)
    }

    fun getLatestWorkoutsLastMonth(): List<JimWorkoutHistoryWidget> {
        val after = Clock.System.now().minus(30.days)
        return WorkoutEntryTable.getAllAfter(date = after).map {
            JimWorkoutHistoryWidget(
                id = it.id,
                finishDate = it.finishTime!! // shouldn't be null here
            )
        }
    }

    fun getCalendarEntries(now: Instant): List<JimCalendarWidgetEntry> {
        val after = now.minus(6.days)

        val workouts = WorkoutEntryTable.getAllAfter(after)
        return buildList {
            for (i in 6 downTo 0) {
                val startOfDay = now.minus(i.days)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
                    .atTime(hour = 0, minute = 0, second = 0)
                val endOfDay = now.minus(i.days)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
                    .atTime(hour = 23, minute = 59, second = 59)

                add(
                    workouts.firstOrNull {
                        it.finishTime?.let { finishTime ->
                            finishTime.toLocalDateTime(TimeZone.currentSystemDefault()) in startOfDay..endOfDay
                        } ?: false
                    }?.let {
                        JimCalendarWidgetEntry(
                            date = it.finishTime!!,
                            hasWorkedOut = true
                        )
                    } ?: JimCalendarWidgetEntry(
                        date = now.minus(i.days),
                        hasWorkedOut = false
                    )
                )
            }
        }
    }
}

@Composable
fun MainView(
    vm: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * Heading
         */
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(Res.string.readyToWorkQuestion),
                style = MaterialTheme.typography.headlineLarge
            )
        }
        /**
         * Calendar Part
         */
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            JimCalendarWidget(
                dates = vm.getCalendarEntries(
                    now = Clock.System.now()
                )
            )
        }
        /**
         * Plans
         */
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.yourPlans),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Row {
                    JimWorkoutPlansWidget(
                        plans = vm.getWorkoutPlans(),
                        onClick = { planId ->
                            // TODO: navigate to plan overview bottom sheet for each id
                        },
                        onStart = { planId ->
                            // TODO: navigate to plan run view
                        }
                    )
                }
            }
        }
        /**
         * Latest Workouts
         */
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.latestWorkouts),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Row {
                    JimWorkoutHistoryWidget(
                        workouts = vm.getLatestWorkoutsLastMonth(),
                        onClick = { workoutId ->
                            // TODO: navigate to bottom sheet with more information
                        }
                    )
                }
            }
        }
    }
}