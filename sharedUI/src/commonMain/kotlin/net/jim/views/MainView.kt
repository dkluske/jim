@file:OptIn(ExperimentalUuidApi::class)

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
import net.jim.components.*
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi

data class MainViewModel(
    override val root: Root
) : JimViewModel {
    fun getWorkoutPlans(): List<JimWorkoutWidgetPlan> {
        // TODO: fetch from DB
        return listOf()
    }

    fun getLatestWorkoutsLastMonth(): List<JimWorkoutHistoryWidget> {
        // TODO: fetch from DB
        return listOf()
    }

    fun getCalendarEntries(now: Instant): List<JimCalendarWidgetEntry> {
        return getDaysBeforeAndAfter(
            now = now,
            days = 3
        ).mapIndexed { index, instant -> // TODO: add real logic whether user has worked out on that day
            JimCalendarWidgetEntry(
                date = instant,
                hasWorkedOut = if (index % 2 == 0) {
                    true
                } else {
                    false
                }
            )
        }
    }

    private fun getDaysBeforeAndAfter(now: Instant, days: Int): List<Instant> {
        val sanitizedDays = if (days < 0) {
            1
        } else {
            days
        }
        return buildList {
            for (i in sanitizedDays downTo 1) {
                add(now.minus(i.days))
            }
            add(now)
            for (i in 1..sanitizedDays) {
                add(now.plus(i.days))
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
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * Heading
         */
        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
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
            modifier = Modifier.fillMaxWidth().padding(4.dp),
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