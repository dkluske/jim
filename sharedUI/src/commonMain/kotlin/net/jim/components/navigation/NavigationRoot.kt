package net.jim.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import jim.sharedui.generated.resources.Res
import jim.sharedui.generated.resources.save
import net.jim.components.JimBottomBar
import net.jim.components.JimWorkoutPlanPartModalBottomSheet
import net.jim.components.JimWorkoutPlanPartModalBottomSheetViewModel
import net.jim.components.navigation.BottomSheetSceneStrategy.Companion.bottomSheet
import net.jim.components.navigation.NavRoute.*
import net.jim.views.*
import org.jetbrains.compose.resources.stringResource

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

                is WorkoutPlanRoute -> {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                ).clickable {
                                    // TODO: save workout plan logic
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = "save workout plan",
                                    modifier = Modifier.padding(end = 4.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    text = stringResource(Res.string.save),
                                    style = MaterialTheme.typography.displayMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    )
}