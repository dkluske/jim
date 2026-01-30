@file:OptIn(ExperimentalUuidApi::class)

package net.jim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import net.jim.theme.AppTheme
import net.jim.views.MainView
import net.jim.views.MainViewModel
import net.jim.views.Root
import kotlin.uuid.ExperimentalUuidApi

@Preview
@Composable
fun App(
) = AppTheme {
    val root = remember { Root() }
    MainView(
        vm = MainViewModel(
            root = root
        )
    )
}
