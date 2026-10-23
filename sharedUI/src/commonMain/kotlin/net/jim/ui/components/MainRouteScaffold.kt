package net.jim.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.jim.Root
import net.jim.ui.HomeRoute
import net.jim.ui.SettingsRoute
import net.jim.ui.StatsRoute

@Composable
fun MainRouteScaffold(
    root: Root,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Content
                Column {
                    Row(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(percent = 50)
                            ).border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(percent = 50)
                            )
                    ) {
                        IconButton(
                            onClick = { root.navStack.value += HomeRoute }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "home"
                            )
                        }
                        IconButton(
                            onClick = { root.navStack.value += StatsRoute }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "stats"
                            )
                        }
                        IconButton(
                            onClick = { root.navStack.value += SettingsRoute }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "settings"
                            )
                        }
                    }
                }
                /**
                 * Run Button
                 * TODO: implement
                 */
                Column {

                }
            }
        }
    ) {
        val contentScrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(contentScrollState)
        ) {
            content()
        }
    }
}