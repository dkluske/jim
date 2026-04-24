package net.jim.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import net.jim.components.navigation.NavRoute
import net.jim.components.navigation.NavStack
import kotlin.uuid.Uuid

data class Root(
    val navStack: MutableState<NavStack<NavRoute>>,
    val runningPlan: MutableState<Uuid?> = mutableStateOf(null)
)