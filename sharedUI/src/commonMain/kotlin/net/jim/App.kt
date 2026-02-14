package net.jim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import jim.sharedui.generated.resources.Res
import kotlinx.coroutines.launch
import net.jim.data.DatabaseDriverManager
import net.jim.data.JimDatabaseManager
import net.jim.data.models.JsonExercise
import net.jim.data.table.JsonExerciseTable
import net.jim.theme.AppTheme
import net.jim.views.MainView
import net.jim.views.MainViewModel
import net.jim.views.Root

@Composable
fun App(
    databaseDriverManager: DatabaseDriverManager
) = AppTheme {
    val database = JimDatabaseManager.createDatabase(databaseDriverManager)
    JimDatabaseManager.initTables(database)

    val appCoroutineScope = rememberCoroutineScope()

    appCoroutineScope.launch {
        ((JsonExerciseTable.getMaxStoredRevision() + 1)..JsonExerciseTable.MAX_REVISION).flatMap {
            val bytes = Res.readBytes("files/exercises/exercises_$it.json")
            JimDatabaseManager.json.decodeFromString<List<JsonExercise>>(bytes.decodeToString())
        }.forEach { exercise ->
            JsonExerciseTable.save(exercise)
        }
    }

    val root = remember { Root() }
    MainView(
        vm = MainViewModel(
            root = root
        )
    )
}