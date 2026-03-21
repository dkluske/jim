package net.jim.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.cash.paging.compose.collectAsLazyPagingItems
import jim.sharedui.generated.resources.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.datetime.toDateTimePeriod
import net.jim.components.utils.JimCard
import net.jim.data.models.JsonExerciseType
import net.jim.data.models.PhysicalJsonExercise
import net.jim.data.models.WorkoutPlanExercise
import net.jim.data.models.WorkoutPlanPart
import net.jim.data.paging.JsonExercisePagingSource
import net.jim.data.table.JsonExerciseTable
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration
import kotlin.uuid.Uuid

data class JimWorkoutPlanPartModalBottomSheetViewModel(
    val workoutPlanPart: WorkoutPlanPart? = null,
    val onDismissRequest: () -> Unit
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
            vm.onDismissRequest()
        }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
                .fillMaxSize()
        ) {
            JimEditableHeader(
                value = name.value ?: stringResource(Res.string.newWorkoutPlanPart),
                onValueChange = { name.value = it },
            )
            JimCard {
                PrimaryTabRow(
                    selectedTabIndex = pagerViewState.currentPage,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    divider = {}
                ) {
                    Tab(
                        selected = pagerViewState.currentPage == BottomSheetViewState.PARTS_EDIT.ordinal,
                        onClick = {
                            coroutineScope.launch {
                                pagerViewState.animateScrollToPage(BottomSheetViewState.PARTS_EDIT.ordinal)
                            }
                        },
                        modifier = Modifier.clip(RoundedCornerShape(6.dp))
                    ) {
                        Text(
                            text = stringResource(Res.string.editExercises),
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                    Tab(
                        selected = pagerViewState.currentPage == BottomSheetViewState.EXERCISE_SEARCH.ordinal,
                        onClick = {
                            coroutineScope.launch {
                                pagerViewState.animateScrollToPage(BottomSheetViewState.EXERCISE_SEARCH.ordinal)
                            }
                        },
                        modifier = Modifier.clip(RoundedCornerShape(6.dp))
                    ) {
                        Text(
                            text = stringResource(Res.string.searchExercises),
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.displayMedium
                        )
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
                                if (exerciseList.isNotEmpty()) {
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
                                } else {
                                    item {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(8.dp),
                                            horizontalArrangement = Arrangement.Center,
                                        ) {
                                            Text(
                                                text = stringResource(Res.string.noExercisesYesPleaseAddOne),
                                                style = MaterialTheme.typography.displaySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        BottomSheetViewState.EXERCISE_SEARCH -> {
                            val textFieldState = rememberTextFieldState()
                            val loadResultListState = rememberLazyListState()
                            val pager = Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
                                JsonExercisePagingSource(textFieldState.text.toString())
                            }.flow.cachedIn(MainScope())
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                            ) {
                                TextField(
                                    state = textFieldState,
                                    placeholder = {
                                        Text(
                                            text = stringResource(Res.string.search),
                                            style = MaterialTheme.typography.displaySmall
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                textFieldState.clearText()
                                            },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "clear search"
                                            )
                                        }
                                    },
                                    contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                                        top = 12.dp,
                                        bottom = 12.dp,
                                        start = 8.dp,
                                        end = 8.dp,
                                    ),

                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        errorIndicatorColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    textStyle = MaterialTheme.typography.displaySmall,
                                    modifier = Modifier.fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = RoundedCornerShape(8.dp)
                                        ).heightIn(min = 20.dp)
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(8.dp),
                                color = MaterialTheme.colorScheme.outline,
                                thickness = 1.dp
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                            ) {
                                // TODO: vertical pager using vm.searchByName ...
                                val searchResults = pager.collectAsLazyPagingItems()
                                LazyColumn(
                                    state = loadResultListState
                                ) {
                                    items(searchResults.itemCount) { index ->
                                        searchResults[index]?.let { resultItem ->
                                            Card {
                                                ListItem(
                                                    headlineContent = {
                                                        Text(
                                                            text = resultItem.name
                                                        )
                                                    },
                                                    trailingContent = {
                                                        IconButton(
                                                            onClick = {
                                                                exerciseList.add(
                                                                    WorkoutPlanExercise(
                                                                        id = Uuid.random(),
                                                                        index = exerciseList.size,
                                                                        jsonExerciseId = resultItem.id,
                                                                        repetitionInterval = when (resultItem) {
                                                                            is PhysicalJsonExercise -> WorkoutPlanExercise.Repeating(
                                                                                repetitions = listOf(
                                                                                    WorkoutPlanExercise.Repeating.Repetition(
                                                                                        repetitions = 12,
                                                                                        weight = null
                                                                                    )
                                                                                )
                                                                            )
                                                                        }
                                                                    )
                                                                )
                                                            }
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Add,
                                                                contentDescription = "add exercise"
                                                            )
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
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