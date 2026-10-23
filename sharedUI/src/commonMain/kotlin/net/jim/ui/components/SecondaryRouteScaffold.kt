package net.jim.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.jim.views.Root

@Composable
fun SecondaryRouteScaffold(
    root: Root,
    title: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        root.navStack.value.removeLastOrNull()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
                title()
            }
        }
    ) {
        val contentScrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(contentScrollState),
        ) {
            content()
        }
    }
}