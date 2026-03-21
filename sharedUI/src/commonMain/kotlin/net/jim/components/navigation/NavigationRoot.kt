package net.jim.components.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import net.jim.components.JimBottomBar
import net.jim.components.JimWorkoutPlanPartModalBottomSheet
import net.jim.components.JimWorkoutPlanPartModalBottomSheetViewModel
import net.jim.components.navigation.BottomSheetSceneStrategy.Companion.bottomSheet
import net.jim.components.navigation.NavRoute.*
import net.jim.views.*

@Composable
fun NavigationRoot(root: Root) {
    val dialogSceneStrategy = remember { DialogSceneStrategy<NavRoute>() }
    val bottomSheetSceneStrategy = remember { BottomSheetSceneStrategy<NavRoute>() }
    val backStack = remember { NavStack<NavRoute>(MainRoute) }

    Scaffold(
        content = {
            Surface(
                content = {
                    NavDisplay(
                        backStack = backStack,
                        sceneStrategy = dialogSceneStrategy.then(bottomSheetSceneStrategy)
                    ) { key ->
                        when (key) {
                            is MainRoute -> {
                                NavEntry(key) {
                                    MainView(
                                        vm = MainViewModel(
                                            root = root,
                                            onNavigateToWorkoutPlan = { workoutPlanId ->
                                                backStack += WorkoutPlanRoute(workoutPlanId)
                                            }
                                        )
                                    )
                                }
                            }

                            is WorkoutPlanRoute -> {
                                NavEntry(key) {
                                    WorkoutPlanView(
                                        vm = WorkoutPlanViewModel(
                                            root = root,
                                            workoutId = key.workoutPlanId,
                                            onNavigateBack = {
                                                if (backStack.size > 1) {
                                                    backStack.removeLast()
                                                }
                                            },
                                            onOpenPlanPart = {
                                                backStack += BottomSheet.JimWorkoutPlanPartModalBottomSheet(
                                                    workoutPlanPart = it
                                                )
                                            }
                                        )
                                    )
                                }
                            }

                            is BottomSheet.JimWorkoutPlanPartModalBottomSheet -> {
                                NavEntry(
                                    key = key,
                                    metadata = bottomSheet()
                                ) {
                                    JimWorkoutPlanPartModalBottomSheet(
                                        vm = JimWorkoutPlanPartModalBottomSheetViewModel(
                                            workoutPlanPart = key.workoutPlanPart,
                                            onDismissRequest = {
                                                backStack.remove(key)
                                            }
                                        )
                                    )
                                }
                            }
                        }
                    }
                },
                color = MaterialTheme.colorScheme.background
            )
        },
        bottomBar = {
            when (backStack.lastOrNull()) {
                is RootRoute -> {
                    JimBottomBar(
                        onAddWorkoutPlan = {
                            backStack += WorkoutPlanRoute(null)
                        }
                    )
                }

                else -> {}
            }
        }
    )
}