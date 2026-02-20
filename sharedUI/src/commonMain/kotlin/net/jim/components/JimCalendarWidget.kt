package net.jim.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jim.sharedui.generated.resources.*
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant

data class JimCalendarWidgetEntry(
    val date: Instant,
    val hasWorkedOut: Boolean
)

@Composable
fun JimCalendarWidget(
    dates: List<JimCalendarWidgetEntry>
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(dates) { date ->
            JimCalendarWidgetEntry(value = date)
        }
    }
}

@Composable
private fun JimCalendarWidgetEntry(
    value: JimCalendarWidgetEntry
) {
    val date = value.date.toLocalDateTime(TimeZone.currentSystemDefault())
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(
            color = if (value.hasWorkedOut) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.Transparent
            },
            shape = RoundedCornerShape(8.dp)
        ).padding(start = 12.dp, end = 12.dp)
    ) {
        Row(
            modifier = Modifier.padding(top = 0.dp, bottom = 8.dp),
        ) {
            Text(
                text = date.dayOfWeek.toAbbreviatedString(),
                style = MaterialTheme.typography.displaySmall,
                color = if (value.hasWorkedOut) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 0.dp),
        ) {
            Text(
                text = date.day.toString(),
                style = MaterialTheme.typography.displaySmall,
                color = if (value.hasWorkedOut) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}

@Composable
private fun DayOfWeek.toAbbreviatedString(): String {
    return when (this) {
        DayOfWeek.MONDAY -> stringResource(Res.string.mondayAbbreviated)
        DayOfWeek.TUESDAY -> stringResource(Res.string.tuesdayAbbreviated)
        DayOfWeek.WEDNESDAY -> stringResource(Res.string.wednesdayAbbreviated)
        DayOfWeek.THURSDAY -> stringResource(Res.string.thursdayAbbreviated)
        DayOfWeek.FRIDAY -> stringResource(Res.string.fridayAbbreviated)
        DayOfWeek.SATURDAY -> stringResource(Res.string.saturdayAbbreviated)
        DayOfWeek.SUNDAY -> stringResource(Res.string.sundayAbbreviated)
    }
}