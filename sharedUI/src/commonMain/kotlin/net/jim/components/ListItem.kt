package net.jim.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = contentAlignment
        ) {
            content()
        }
    }
}