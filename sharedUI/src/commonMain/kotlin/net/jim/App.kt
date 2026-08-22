package net.jim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import jim.sharedui.generated.resources.Res
import kotlinx.coroutines.launch
import net.jim.components.navigation.NavRoute
import net.jim.components.navigation.NavRoute.MainRoute
import net.jim.components.navigation.NavStack
import net.jim.components.navigation.NavigationRoot
import net.jim.data.DatabaseDriverManager
import net.jim.data.JimDatabaseManager
import net.jim.data.models.JsonExerciseChangeset
import net.jim.data.table.JsonExerciseTable
import net.jim.theme.AppTheme
import net.jim.views.Root

@Composable
fun App(
    databaseDriverManager: DatabaseDriverManager
) = AppTheme {
    val database = JimDatabaseManager.createDatabase(databaseDriverManager)
    JimDatabaseManager.initTables(database)

    val appCoroutineScope = rememberCoroutineScope()

    appCoroutineScope.launch {
        ((JsonExerciseTable.getMaxStoredRevision() + 1)..JsonExerciseTable.MAX_REVISION).forEach { revision ->
            val bytes = Res.readBytes("files/exercises/exercises_$revision.json")
            JimDatabaseManager.json.decodeFromString<JsonExerciseChangeset>(bytes.decodeToString()).let {
                it.jsonExercises.forEach { exercise ->
                    when (it.dbOperationType) {
                        JsonExerciseChangeset.DBOperationType.INSERT -> JsonExerciseTable.save(entity = exercise)
                        JsonExerciseChangeset.DBOperationType.UPDATE -> JsonExerciseTable.update(
                            id = exercise.id,
                            entity = exercise
                        )

                        JsonExerciseChangeset.DBOperationType.DELETE -> JsonExerciseTable.delete(id = exercise.id)
                    }
                }
            }
        }
    }
    val backStack = remember { mutableStateOf(NavStack<NavRoute>(MainRoute)) }

    val root = remember {
        Root(
            navStack = backStack
        )
    }
    NavigationRoot(
        root = root
    )
}