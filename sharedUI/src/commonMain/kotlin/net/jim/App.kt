package net.jim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import jim.sharedui.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.jim.data.DatabaseDriverManager
import net.jim.data.JimDatabaseManager
import net.jim.data.TableHolder
import net.jim.data.models.JsonExerciseChangeset
import net.jim.data.table.JsonExerciseTable
import net.jim.theme.AppTheme
import net.jim.ui.HomeRoute
import net.jim.ui.Route
import net.jim.ui.navigation.NavStack
import net.jim.ui.navigation.NavigationRoot

@Composable
fun App(
    databaseDriverManager: DatabaseDriverManager
) = AppTheme {
    val database = JimDatabaseManager.createDatabase(databaseDriverManager)
    val tableHolder = TableHolder(database)

    val backStack = remember { mutableStateOf(NavStack<Route>(HomeRoute)) }

    val root = remember {
        Root(
            navStack = backStack
        )
    }
    NavigationRoot(
        root = root,
        tableHolder = tableHolder
    )

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            loadMissingRevisions(tableHolder.jsonExerciseTable)
        }
    }
}

private suspend fun loadMissingRevisions(jsonExerciseTable: JsonExerciseTable) {
    ((jsonExerciseTable.getMaxStoredRevision() + 1)..jsonExerciseTable.MAX_REVISION).forEach { revision ->
        val bytes = Res.readBytes("files/exercises/exercises_$revision.json")
        JimDatabaseManager.json.decodeFromString<JsonExerciseChangeset>(bytes.decodeToString()).let {
            it.exercises.forEach { exercise ->
                when (it.dbOperationType) {
                    JsonExerciseChangeset.DBOperationType.INSERT -> jsonExerciseTable.save(entity = exercise)
                    JsonExerciseChangeset.DBOperationType.UPDATE -> jsonExerciseTable.update(
                        id = exercise.id,
                        entity = exercise
                    )

                    JsonExerciseChangeset.DBOperationType.DELETE -> jsonExerciseTable.delete(id = exercise.id)
                }
            }
        }
    }
}