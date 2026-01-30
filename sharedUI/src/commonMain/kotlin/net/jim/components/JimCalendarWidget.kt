package net.jim.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
}

@Composable
private fun JimCalendarWidgetEntry(
    value: JimCalendarWidgetEntry
) {
    val date = value.date.toLocalDateTime(TimeZone.currentSystemDefault())
    Column {
        Row {
            Text(
                text = date.dayOfWeek.toAbbreviatedString(),
                style = MaterialTheme.typography.displaySmall
            )
        }
        Row {
            Box(
                modifier = Modifier.background(
                    color = if (value.hasWorkedOut) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    shape = CircleShape
                )
            )
        }
        Row {
            Text(
                text = date.day.toString(),
                style = MaterialTheme.typography.displaySmall
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