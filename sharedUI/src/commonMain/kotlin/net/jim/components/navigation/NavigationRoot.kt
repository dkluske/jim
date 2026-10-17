package net.jim.components.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import net.jim.components.JimWorkoutPlanPartModalBottomSheet
import net.jim.components.JimWorkoutPlanPartModalBottomSheetViewModel
import net.jim.components.navigation.BottomSheetSceneStrategy.Companion.bottomSheet
import net.jim.components.navigation.NavRoute.MainRoute
import net.jim.components.navigation.NavRoute.WorkoutPlanRoute
import net.jim.data.models.WorkoutPlanPart
import net.jim.views.*
import kotlin.uuid.Uuid

@Composable
fun NavigationRoot(root: Root) {
    val dialogSceneStrategy = remember { DialogSceneStrategy<NavRoute>() }
    val bottomSheetSceneStrategy = remember { BottomSheetSceneStrategy<NavRoute>() }

    Surface(
        content = {
            NavDisplay(
                backStack = root.navStack.value,
                sceneStrategy = dialogSceneStrategy.then(bottomSheetSceneStrategy)
            ) { key ->
                when (key) {
                    is MainRoute -> {
                        NavEntry(key) {
                            MainView(
                                vm = MainViewModel(
                                    root = root,
                                    onNavigateToWorkoutPlan = { workoutPlanId ->
                                        root.navStack.value += WorkoutPlanRoute(workoutPlanId)
                                    }
                                )
                            )
                        }
                    }

                    is WorkoutPlanRoute -> {
                        NavEntry(key) {
                            val planParts = remember { mutableStateListOf<WorkoutPlanPart>() }
                            WorkoutPlanView(
                                vm = WorkoutPlanViewModel(
                                    root = root,
                                    workoutId = key.workoutPlanId,
                                    onNavigateBack = {
                                        if (root.navStack.value.size > 1) {
                                            root.navStack.value.removeLast()
                                        }
                                    },
                                    planParts = planParts,
                                    onOpenPlanPart = {
                                        root.navStack.value += BottomSheet.JimWorkoutPlanPartModalBottomSheet(
                                            workoutPlanId = key.workoutPlanId ?: Uuid.random(),
                                            workoutPlanPart = it
                                        ) { planPart ->
                                            planParts.add(planPart)
                                        }
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
                                    workoutPlanId = key.workoutPlanId,
                                    onConfirm = key.onConfirm,
                                    workoutPlanPart = key.workoutPlanPart,
                                    onDismissRequest = {
                                        root.navStack.value.remove(key)
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
}