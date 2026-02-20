package net.jim.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JimBottomBar() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ).background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            )
        ) {
            // TODO: expect / actual for taskbar to make use of liquid glass on ios
            Row {
                IconButton(
                    onClick = { /* TODO: implement navigation logic */ }
                ) {
                    Icon(Icons.Filled.Home, contentDescription = "home")
                }
                IconButton(
                    onClick = { /* TODO: implement navigation logic */ }
                ) {
                    // TODO: better icon for statistics
                    Icon(Icons.Filled.Refresh, contentDescription = "statistics")
                }
                IconButton(
                    onClick = { /* TODO: implement navigation logic */ }
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "fast start")
                }
            }
        }
        Column(
            modifier = Modifier.border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ).background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            )
        ) {
            IconButton(
                onClick = {
                    // TODO: navigate to edit workout plan with no id
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        }
    }

}