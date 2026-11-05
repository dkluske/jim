package net.jim.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Badge(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(percent = 50)
        ).background(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            shape = RoundedCornerShape(percent = 50)
        )
    ) {
        content()
    }
}