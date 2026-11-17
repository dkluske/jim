package net.jim.ui.routes.workout

import net.jim.Root
import net.jim.data.table.WorkoutEntryTable
import net.jim.ui.Route
import net.jim.ui.routes.util.ViewModel

data class WorkoutEntryListViewModel(
    override val root: Root,
    override val routeOrigin: Route,
    private val workoutEntryTable: WorkoutEntryTable,
) : ViewModel
