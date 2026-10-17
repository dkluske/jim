package net.jim.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp

@Composable
fun JimEditableHeader(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val editEnabled = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(editEnabled.value) {
        if (editEnabled.value) {
            focusRequester.requestFocus()
        }
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (editEnabled.value) {
            val textFieldState = rememberTextFieldState(value)
            fun onDone() {
                onValueChange(textFieldState.text.toList().joinToString(""))
                editEnabled.value = false
            }

            TextField(
                state = textFieldState,
                lineLimits = TextFieldLineLimits.SingleLine,
                trailingIcon = {
                    IconButton(onClick = {
                        onDone()
                    }) {
                        Icon(Icons.Filled.Done, contentDescription = "Done")
                    }
                },
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    top = 12.dp,
                    bottom = 12.dp,
                    start = 8.dp,
                    end = 8.dp,
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester).heightIn(min = 20.dp)
                    .onKeyEvent { event ->
                        if (event.key == Key.Enter) {
                            onDone()
                            true
                        } else {
                            false
                        }
                    }
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = value
                    )
                }
                Column {
                    IconButton(
                        onClick = {
                            editEnabled.value = !editEnabled.value
                        }
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                }
            }
        }
    }
}