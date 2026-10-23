package net.jim.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable
import net.jim.ui.Route
import net.jim.ui.navigation.NavStack
import kotlin.uuid.Uuid

@Serializable
data class Root(
    val navStack: MutableState<NavStack<Route>>,
    val runningPlan: MutableState<Uuid?> = mutableStateOf(null)
)