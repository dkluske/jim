package net.jim.components.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.jim.components.JimBottomBar
import net.jim.views.*

@Composable
fun NavigationRoot(root: Root) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(NavRoute::class)
                    subclass(NavRoute.RootRoute::class)
                    subclass(NavRoute.MainRoute::class)
                    subclass(NavRoute.WorkoutPlanRoute::class)
                }
                polymorphic(NavRoute::class) {
                    subclass(NavRoute.RootRoute::class)
                    subclass(NavRoute.MainRoute::class)
                    subclass(NavRoute.WorkoutPlanRoute::class)
                }
                polymorphic(NavRoute.RootRoute::class) {
                    subclass(NavRoute.MainRoute::class)
                }
            }
        },
        NavRoute.MainRoute
    )

    Scaffold(
        content = {
            Surface(
                content = {
                    NavDisplay(
                        backStack = backStack
                    ) { key ->
                        when (key) {
                            is NavRoute.MainRoute -> {
                                NavEntry(key) {
                                    MainView(
                                        vm = MainViewModel(
                                            root = root,
                                            onNavigateToWorkoutPlan = { workoutPlanId ->
                                                backStack.add(NavRoute.WorkoutPlanRoute(workoutPlanId))
                                            }
                                        )
                                    )
                                }
                            }

                            is NavRoute.WorkoutPlanRoute -> {
                                NavEntry(key) {
                                    WorkoutPlanView(
                                        vm = WorkoutPlanViewModel(
                                            root = root,
                                            workoutId = key.workoutPlanId,
                                            onNavigateBack = {
                                                if (backStack.size > 1) {
                                                    backStack.removeLast()
                                                }
                                            }
                                        )
                                    )
                                }
                            }

                            else -> {
                                throw IllegalStateException("Unknown key $key")
                            }
                        }
                    }
                },
                color = MaterialTheme.colorScheme.background
            )
        },
        bottomBar = {
            when (backStack.lastOrNull()) {
                is NavRoute.RootRoute -> {
                    JimBottomBar(
                        onAddWorkoutPlan = {
                            backStack.add(NavRoute.WorkoutPlanRoute(null))
                        }
                    )
                }
            }
        }
    )
}