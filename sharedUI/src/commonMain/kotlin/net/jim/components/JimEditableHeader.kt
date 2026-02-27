package net.jim.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun JimEditableHeader(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val editEnabled = remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {

    }
    if (editEnabled.value) {
        TextField(
            value = value,
            onValueChange = onValueChange
        )
    } else {
        Text(
            text = value
        )
    }
    IconButton(
        onClick = {
            editEnabled.value = !editEnabled.value
        }
    ) {
        if (editEnabled.value) {
            Icon(Icons.Filled.Done, contentDescription = "Done")
        } else {
            Icon(Icons.Filled.Edit, contentDescription = "Edit")
        }
    }
}