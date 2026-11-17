package net.jim.ui.routes.workout

import net.jim.Root
import net.jim.data.models.Identifier
import net.jim.data.table.WorkoutEntryTable
import net.jim.ui.Route
import net.jim.ui.routes.util.ViewModel

data class WorkoutEntryDetailViewModel(
    override val root: Root,
    override val routeOrigin: Route,
    val workoutIdentifier: Identifier,
    private val workoutEntryTable: WorkoutEntryTable
) : ViewModel