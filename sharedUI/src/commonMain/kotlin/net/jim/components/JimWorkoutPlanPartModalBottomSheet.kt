package net.jim.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jim.sharedui.generated.resources.*
import kotlinx.coroutines.launch
import kotlinx.datetime.toDateTimePeriod
import net.jim.components.utils.JimCard
import net.jim.data.models.JsonExerciseType
import net.jim.data.models.WorkoutPlanExercise
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.table.JsonExerciseTable
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration
import kotlin.uuid.Uuid

data class JimWorkoutPlanPartModalBottomSheetViewModel(
    val expanded: Boolean = false,
    val workoutPlanPart: WorkoutPlanPart? = null,
) {
    fun resolveJsonExercise(id: Uuid): JsonExerciseType {
        return JsonExerciseTable.getById(id)
    }

    fun searchByName(name: String, pageSize: Int, offset: Int): List<JsonExerciseType> {
        return JsonExerciseTable.searchByNamePaged(
            name = "%$name%",
            pageSize = pageSize,
            offset = offset
        )
    }
}

private enum class BottomSheetViewState {
    PARTS_EDIT,
    EXERCISE_SEARCH
}

@Composable
fun JimWorkoutPlanPartModalBottomSheet(
    vm: JimWorkoutPlanPartModalBottomSheetViewModel
) {
    if (vm.expanded) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val name = remember { mutableStateOf(vm.workoutPlanPart?.name) }
        val exerciseList = remember { mutableListOf<WorkoutPlanExercise>() }
        val pagerViewState = rememberPagerState(
            initialPage = BottomSheetViewState.PARTS_EDIT.ordinal,
            pageCount = { BottomSheetViewState.entries.size })
        val jsonExerciseMap = remember { mutableStateMapOf<Uuid, JsonExerciseType>() }
        val extendedPart = remember { mutableStateOf<WorkoutPlanExercise?>(null) }
        val coroutineScope = rememberCoroutineScope()
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        ) {
            JimEditableHeader(
                value = name.value ?: stringResource(Res.string.newWorkoutPlanPart),
                onValueChange = { name.value = it },
            )
            JimCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    BottomSheetViewState.entries.forEach {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    pagerViewState.animateScrollToPage(it.ordinal)
                                }
                            },
                            modifier = if (pagerViewState.currentPage == it.ordinal) {
                                Modifier.background(color = MaterialTheme.colorScheme.primary)
                            } else {
                                Modifier
                            }
                        ) {
                            Text(
                                text = it.name
                            )
                        }
                    }
                }
                HorizontalPager(
                    state = pagerViewState
                ) { page ->
                    val pageValue = BottomSheetViewState.entries[page]
                    when (pageValue) {
                        BottomSheetViewState.PARTS_EDIT -> {
                            val lazyListState = rememberLazyListState()
                            LazyColumn(
                                state = lazyListState
                            ) {
                                items(exerciseList.size) { index ->
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                text = (jsonExerciseMap[exerciseList[index].jsonExerciseId]
                                                    ?: vm.resolveJsonExercise(exerciseList[index].id).also {
                                                        jsonExerciseMap[exerciseList[index].jsonExerciseId] = it
                                                    }).name,
                                                modifier = Modifier.clickable {
                                                    if (extendedPart.value == exerciseList[index]) {
                                                        extendedPart.value = null
                                                    } else {
                                                        extendedPart.value = exerciseList[index]
                                                    }
                                                }
                                            )
                                        },
                                        trailingContent = {
                                            // TODO: delete / drag and drop
                                        },
                                        supportingContent = {
                                            // TODO: repetition interval
                                            if (extendedPart.value == exerciseList[index]) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    fun onValueChange(repetitionInterval: WorkoutPlanExercise.RepetitionInterval) {
                                                        exerciseList[index] = exerciseList[index].copy(
                                                            repetitionInterval = repetitionInterval
                                                        )
                                                    }

                                                    Column(
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        when (exerciseList[index].repetitionInterval) {
                                                            is WorkoutPlanExercise.Repeating -> {
                                                                JimWorkoutRepeatingRepetitionInputField(
                                                                    value = WorkoutPlanExercise.Repeating(
                                                                        repetitions = listOf(
                                                                            WorkoutPlanExercise.Repeating.Repetition(
                                                                                repetitions = 12,
                                                                                weight = null
                                                                            )
                                                                        )
                                                                    )
                                                                ) { newValue ->
                                                                    onValueChange(newValue)
                                                                }
                                                            }

                                                            is WorkoutPlanExercise.Timed -> {
                                                                JimWorkoutTimedRepetitionInputField(
                                                                    value = WorkoutPlanExercise.Timed(
                                                                        duration = Duration.ZERO
                                                                    )
                                                                ) { newValue ->
                                                                    onValueChange(newValue)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                                item {
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerViewState.animateScrollToPage(BottomSheetViewState.EXERCISE_SEARCH.ordinal)
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Filled.Search, contentDescription = "Search Exercises")
                                        Text(
                                            text = stringResource(Res.string.searchExercises)
                                        )
                                    }
                                }
                            }
                        }

                        BottomSheetViewState.EXERCISE_SEARCH -> {
                            val textSearch = remember { mutableStateOf("") }
                            TextField(
                                value = textSearch.value,
                                onValueChange = { textSearch.value = it },
                                placeholder = {
                                    Text(text = stringResource(Res.string.search))
                                }
                            )
                            // TODO: vertical pager using vm.searchByName ...
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.JimWorkoutRepeatingRepetitionInputField(
    value: WorkoutPlanExercise.Repeating,
    onValueChange: (WorkoutPlanExercise.RepetitionInterval) -> Unit
) {
    LazyColumn {
        items(value.repetitions.size) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Repetitions
                Column {
                    Button(
                        onClick = { /* TODO: open number dialog */ }
                    ) {
                        Text(
                            text = value.repetitions[index].repetitions.toString()
                        )
                    }
                }
                //Multiplicator
                Column {
                    Text(
                        text = "x"
                    )
                }
                // Weight
                Column {
                    Button(
                        onClick = { /* TODO: open number dialog */ }
                    ) {
                        Text(
                            text = value.repetitions[index].weight?.toString() ?: "-"
                        )
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onValueChange(
                            value.copy(
                                repetitions = value.repetitions.plus(
                                    WorkoutPlanExercise.Repeating.Repetition(
                                        repetitions = value.repetitions.lastOrNull()?.repetitions ?: 12,
                                        weight = value.repetitions.lastOrNull()?.weight
                                    )
                                )
                            )
                        )
                    }
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Add Repetitions")
                    Text(
                        text = stringResource(Res.string.add)
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.JimWorkoutTimedRepetitionInputField(
    value: WorkoutPlanExercise.Timed,
    onValueChange: (WorkoutPlanExercise.RepetitionInterval) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { /* open duration spinner */ }
        ) {
            Text(
                text = value.duration.toDateTimePeriod().let { "${it.hours}:${it.minutes}:${it.seconds}" }
            )
        }
    }
}