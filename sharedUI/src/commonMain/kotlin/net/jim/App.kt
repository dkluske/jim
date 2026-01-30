package net.jim

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import net.jim.theme.AppTheme
import net.jim.views.MainView
import net.jim.views.MainViewModel

@Preview
@Composable
fun App(
) = AppTheme {
    MainView(
        vm = MainViewModel,
    )
}
