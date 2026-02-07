package net.jim.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlin.uuid.Uuid

data class Root(
    val runningPlan: MutableState<Uuid?> = mutableStateOf(null)
)