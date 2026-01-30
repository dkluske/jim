package net.jim.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            onClick = {
                // TODO: navigate to edit workout plan with no id
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add"
            )
        }
    }
}