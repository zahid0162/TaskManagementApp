package com.zahid.taskmaster.presentation.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.zahid.taskmaster.domain.data.TaskStatus
import com.zahid.taskmaster.domain.enums.SortOrder
import com.zahid.taskmaster.presentation.compose_ui_utils.navigateToNextScreen
import com.zahid.taskmaster.presentation.navigation.TaskScreenRoutes
import com.zahid.taskmaster.presentation.utils.CustomTabRow
import com.zahid.taskmaster.presentation.utils.EmptyTaskList
import com.zahid.taskmaster.presentation.utils.SwipeableTaskItem
import com.zahid.taskmaster.presentation.viewmodels.TaskEvent
import com.zahid.taskmaster.presentation.viewmodels.TaskUiEvents
import com.zahid.taskmaster.presentation.viewmodels.TaskViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.*

@Composable
fun TaskListScreen(navController: NavHostController, viewModel: TaskViewModel) {
    val taskState by viewModel.taskState.collectAsStateWithLifecycle()
    var selectedStatus by remember { mutableStateOf<TaskStatus?>(null) }
    var sortOrder by remember { mutableStateOf(SortOrder.PRIORITY) }
    val snackbarHostState = remember { SnackbarHostState() }
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(true) {
        viewModel.onEvent(TaskEvent.FetchAllTasks)
    }

    LaunchedEffect(viewModel.uiEvents) {
        viewModel.uiEvents.collect {
            when (it) {
                TaskUiEvents.ClearSnackBar -> Unit
                is TaskUiEvents.ShowSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = if (it.showUndo) "Undo" else null,
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                    when (result) {
                        androidx.compose.material3.SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(TaskEvent.UndoDeleteTask)
                        }
                        androidx.compose.material3.SnackbarResult.Dismissed -> {
                            viewModel.onEvent(TaskEvent.ClearSnackbar)
                        }
                    }
                }
            }
        }
    }

    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            viewModel.onEvent(TaskEvent.ReorderTasksPreview(from.index, to.index))
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        },
        onDragEnd = { from, to ->
            if (from != to) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                viewModel.onEvent(TaskEvent.ReorderTasks(from, to))
            }
        },
        dragCancelledAnimation = SpringDragCancelledAnimation(stiffness = Spring.StiffnessLow),
        maxScrollPerFrame = 20.dp,
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TaskListTopBar(
                selectedStatus = selectedStatus,
                onStatusSelected = { status ->
                    selectedStatus = status
                    if (status == null) {
                        viewModel.onEvent(TaskEvent.FetchAllTasks)
                    } else {
                        viewModel.onEvent(TaskEvent.FetchTasksByStatus(status))
                    }
                },
                onSortOrderSelected = {
                    sortOrder = it
                    viewModel.onEvent(TaskEvent.SortTasks(it))
                }
            )
        },
        floatingActionButton = {
            var isPressed by remember { mutableStateOf(false) }

            FloatingActionButton(
                onClick = {
                    isPressed = true
                    viewModel.viewModelScope.launch {
                        delay(100)
                        isPressed = false
                        navController.navigate(TaskScreenRoutes.CREATE_TASK.route)
                    }
                },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .scale(
                        animateFloatAsState(
                            targetValue = if (isPressed) 0.85f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "FAB Scale"
                        ).value
                    )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create Task",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                taskState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                taskState.error != null -> {
                    Text(
                        "Error: ${taskState.error}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                taskState.tasks.isEmpty() -> {
                    EmptyTaskList(
                        modifier = Modifier.align(Alignment.Center),
                        onAddTaskClick = {
                            navController.navigateToNextScreen<Unit>(
                                route = TaskScreenRoutes.CREATE_TASK.route,
                                shouldPopBackStack = false
                            )
                        }
                    )
                }

                else -> {
                    LazyColumn(
                        state = reorderableState.listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .reorderable(reorderableState),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(
                            items = taskState.tasks,
                            key = { _, task -> task.id }
                        ) { index, task ->
                            ReorderableItem(
                                reorderableState = reorderableState,
                                key = task.id,
                                modifier = Modifier.animateItem(
                                    fadeInSpec = null, fadeOutSpec = null, placementSpec = tween(durationMillis = 300)
                                )
                            ) { isDragging ->
                                SwipeableTaskItem(
                                    task = task,
                                    onComplete = {
                                        viewModel.onEvent(
                                            TaskEvent.UpdateTaskStatus(
                                                task.id,
                                                TaskStatus.COMPLETED
                                            )
                                        )
                                    },
                                    onDelete = { viewModel.onEvent(TaskEvent.DeleteTask(task)) },
                                    onClick = {
                                        navController.navigate("${TaskScreenRoutes.TASK_DETAIL.route}/${task.id}")
                                    },
                                    isDragging = isDragging,
                                    dragModifier = Modifier.detectReorderAfterLongPress(reorderableState)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskListTopBar(
    selectedStatus: TaskStatus?,
    onStatusSelected: (TaskStatus?) -> Unit,
    onSortOrderSelected: (SortOrder) -> Unit
) {
    var showSortMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopAppBar(
            title = { Text("Tasks") },
            actions = {
                IconButton(onClick = { showSortMenu = true }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Sort Tasks"
                    )
                }
                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false }
                ) {
                    SortOrder.entries.forEach { order ->
                        DropdownMenuItem(
                            text = { Text(order.displayName) },
                            onClick = {
                                onSortOrderSelected(order)
                                showSortMenu = false
                            }
                        )
                    }
                }
            }
        )

        CustomTabRow(
            selectedStatus = selectedStatus,
            onStatusSelected = onStatusSelected
        )
    }
}












