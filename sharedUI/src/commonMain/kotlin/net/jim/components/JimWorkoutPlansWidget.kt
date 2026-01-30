@file:OptIn(ExperimentalUuidApi::class)

package net.jim.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jim.sharedui.generated.resources.Res
import jim.sharedui.generated.resources.noPlansCreateOne
import jim.sharedui.generated.resources.showMore
import net.jim.components.utils.JimCard
import net.jim.components.utils.JimListWrapper
import org.jetbrains.compose.resources.stringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class JimWorkoutWidgetPlan(
    val id: Uuid,
    val name: String
)

@Composable
fun JimWorkoutPlansWidget(
    plans: List<JimWorkoutWidgetPlan>,
    onClick: (Uuid) -> Unit,
    onStart: (Uuid) -> Unit
) {
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
}