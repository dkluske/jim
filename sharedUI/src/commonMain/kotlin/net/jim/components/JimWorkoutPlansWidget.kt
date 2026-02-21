package net.jim.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jim.sharedui.generated.resources.*
import net.jim.components.utils.JimCard
import net.jim.components.utils.JimListWrapper
import net.jim.data.models.JsonExerciseType
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.Uuid

data class JimWorkoutWidgetPlan(
    val id: Uuid,
    val name: String,
    val isDefault: Boolean,
    val primaryMuscles: List<JsonExerciseType.MuscleEnum>
)

@Composable
fun JimWorkoutPlansWidget(
    plans: List<JimWorkoutWidgetPlan>,
    onClick: (Uuid) -> Unit,
    onStart: (Uuid) -> Unit
) {
    if (plans.isEmpty()) {

        JimCard {
            JimListWrapper(
                list = plans,
                wrap = 3,
                onEmpty = {
                    Text(
                        text = stringResource(Res.string.noPlansCreateOne),
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
            ) { plan ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = plan.name,
                            style = MaterialTheme.typography.displaySmall
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { onStart(plan.id) },
                        ) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "start workout")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            onClick(plan.id)
                        }
                )
            }
        }
    } else {
        val scrollState = rememberLazyListState()
        LazyRow(
            state = scrollState
        ) {
            items(plans.size) { index ->
                JimCard {
                    ListItem(
                        headlineContent = {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column {
                                        Text(
                                            text = plans[index].name,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            style = MaterialTheme.typography.displaySmall
                                        )
                                    }
                                    Column {
                                        if (plans[index].isDefault) {
                                            Text(
                                                text = stringResource(Res.string.defaultTag),
                                                color = MaterialTheme.colorScheme.primary,
                                                style = MaterialTheme.typography.displaySmall
                                            )
                                        }
                                    }
                                }
                                Row {
                                    Text(
                                        text = plans[index].primaryMuscles.joinToString(),
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        style = MaterialTheme.typography.displaySmall
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    Button(
                                        onClick = { /* TODO: Start the Workout */ },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.onPrimary,
                                            containerColor = MaterialTheme.colorScheme.primary
                                        ),
                                        modifier = Modifier.padding(8.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = stringResource(Res.string.startButton),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.displaySmall
                                        )
                                    }
                                }
                            }
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                }
            }
        }
    }
}