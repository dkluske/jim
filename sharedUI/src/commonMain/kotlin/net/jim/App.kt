package net.jim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import net.jim.data.DatabaseDriverManager
import net.jim.theme.AppTheme
import net.jim.views.MainView
import net.jim.views.MainViewModel
import net.jim.views.Root

@Composable
fun App(
    databaseDriverManager: DatabaseDriverManager
) = AppTheme {
    val root = remember { Root() }
    MainView(
        vm = MainViewModel(
            root = root
        )
    )
}
