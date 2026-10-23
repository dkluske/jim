package net.jim.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import jim.sharedui.generated.resources.Res
import jim.sharedui.generated.resources.noWorkoutsStartNow
import jim.sharedui.generated.resources.showMore
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.jim.components.utils.ListWrapper
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class WorkoutHistoryWidget(
    val id: Uuid,
    val finishDate: Instant
)

@Composable
fun WorkoutHistoryWidget(
    workouts: List<WorkoutHistoryWidget>,
    onClick: (Uuid) -> Unit,
) {
    Card {
        ListWrapper(
            list = workouts,
            wrap = 3,
            onEmpty = {
                Text(
                    text = stringResource(Res.string.noWorkoutsStartNow),
                    style = MaterialTheme.typography.displaySmall
                )
            },
            onShowMore = {
                Button(
                    onClick = {
                        // TODO: navigate to listview
                    }
                ) {
                    Text(
                        text = stringResource(Res.string.showMore),
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
        ) { workout ->
            ListItem(
                headlineContent = {
                    Text(
                        text = workout.finishDate.toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                trailingContent = {
                    IconButton(
                        onClick = {
                            onClick(workout.id)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "more information")
                    }
                }
            )
        }
    }
}