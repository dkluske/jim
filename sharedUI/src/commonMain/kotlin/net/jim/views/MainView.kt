package net.jim.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jim.sharedui.generated.resources.Res
import jim.sharedui.generated.resources.readyToWorkQuestion
import net.jim.components.JimCalendarWidget
import net.jim.components.JimCalendarWidgetEntry
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

data object MainViewModel {
    fun getDaysBeforeAndAfter(now: Instant, days: Int): List<Instant> {
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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth()
        ) {
            JimCalendarWidget(
                dates = vm.getDaysBeforeAndAfter(
                    now = Clock.System.now(),
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
            )
        }
    }
}