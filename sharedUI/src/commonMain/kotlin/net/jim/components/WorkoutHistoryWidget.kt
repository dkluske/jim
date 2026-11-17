package net.jim.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import jim.sharedui.generated.resources.Res
import jim.sharedui.generated.resources.noWorkoutsStartNow
import jim.sharedui.generated.resources.showMore
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.jim.components.utils.ListWrapper
import net.jim.data.models.Identifier
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class WorkoutHistoryWidget(
    val id: Uuid,
    val finishDate: Instant,
    val workoutPlanPartIdentifier: Identifier
)

@Composable
fun WorkoutHistoryWidget(
    workouts: List<WorkoutHistoryWidget>,
    onClick: (Identifier) -> Unit,
    onShowMore: () -> Unit
) {
    ListWrapper(
        list = workouts,
        wrap = 3,
        onEmpty = {
            ListItem(
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(Res.string.noWorkoutsStartNow),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        },
        onShowMore = {
            Button(
                onClick = {
                    onShowMore()
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
            onClick = {
                onClick(workout.let { Identifier(it.id, it.workoutPlanPartIdentifier.name) })
            }
        ) {
            Text(
                text = workout.finishDate.toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}