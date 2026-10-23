package net.jim.ui.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import net.jim.Root
import net.jim.data.TableHolder
import net.jim.ui.*
import net.jim.ui.routes.home.HomeView
import net.jim.ui.routes.home.HomeViewModel
import net.jim.ui.routes.settings.SettingsView
import net.jim.ui.routes.settings.SettingsViewModel
import net.jim.ui.routes.stats.StatsView
import net.jim.ui.routes.stats.StatsViewModel
import net.jim.ui.routes.workoutPlan.WorkoutPlanView
import net.jim.ui.routes.workoutPlan.WorkoutPlanViewModel
import net.jim.ui.routes.workoutPlanPartRun.WorkoutPlanPartRunView
import net.jim.ui.routes.workoutPlanPartRun.WorkoutPlanPartRunViewModel

@Composable
fun NavigationRoot(
    root: Root,
    tableHolder: TableHolder
) {
    val dialogSceneStrategy = remember { DialogSceneStrategy<Route>() }
    val bottomSheetSceneStrategy = remember { BottomSheetSceneStrategy<Route>() }

    Surface(
        content = {
            NavDisplay(
                backStack = root.navStack.value,
                sceneStrategy = dialogSceneStrategy.then(bottomSheetSceneStrategy)
            ) { key ->
                when (key) {
                    is HomeRoute -> NavEntry(key) {
                        HomeView(
                            viewModel = HomeViewModel(
                                root = root
                            ),
                        )
                    }

                    is SettingsRoute -> NavEntry(key) {
                        SettingsView(
                            viewModel = SettingsViewModel(
                                root = root
                            ),
                        )
                    }

                    is StatsRoute -> NavEntry(key) {
                        StatsView(
                            viewModel = StatsViewModel(
                                root = root
                            ),
                        )
                    }

                    is WorkoutPlanPartRunRoute -> NavEntry(key) {
                        WorkoutPlanPartRunView(
                            viewModel = WorkoutPlanPartRunViewModel(
                                root = root
                            )
                        )
                    }

                    is WorkoutPlanRoute -> NavEntry(key) {
                        WorkoutPlanView(
                            viewModel = WorkoutPlanViewModel(
                                root = root
                            )
                        )
                    }

                    is WorkoutPlanDetailRoute -> TODO()
                    is BottomSheet.JsonExerciseSearchModal -> TODO()
                    is BottomSheet.WorkoutPlanPartSelectionModal -> TODO()
                    is Dialog.ResetAppDialog -> TODO()
                }
            }
        },
        color = MaterialTheme.colorScheme.background
    )
}