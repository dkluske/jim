package net.jim.ui.routes.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jim.ui.components.MainRouteScaffold

@Composable
fun SettingsView(
    viewModel: SettingsViewModel
) {
    MainRouteScaffold(
        root = viewModel.root
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Settings"
            )
            IconButton(
                onClick = {
                    // TODO: reset whole system
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear Settings"
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            // TODO: nice settings content
        }
    }
}