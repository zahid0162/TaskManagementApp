package com.zahid.taskmaster.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LowPriority
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.zahid.taskmaster.R
import com.zahid.taskmaster.domain.data.Task
import com.zahid.taskmaster.domain.data.TaskStatus
import com.zahid.taskmaster.domain.enums.TaskPriority
import com.zahid.taskmaster.presentation.viewmodels.TaskEvent
import com.zahid.taskmaster.presentation.viewmodels.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(navController: NavHostController, viewModel: TaskViewModel) {
    val taskState by viewModel.taskState.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(TaskEvent.ResetTaskForm)
    }

    val isFormValid = taskState.taskName.isNotBlank()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.create_new_task)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isFormValid) {
                        val task = Task(
                            id = UUID.randomUUID().toString(),
                            name = taskState.taskName,
                            description = taskState.taskDescription,
                            priority = taskState.taskPriority,
                            dateTime = taskState.taskDueDate.atStartOfDay(),
                            status = TaskStatus.PENDING,
                            position = 0
                        )
                        viewModel.onEvent(TaskEvent.AddTask(task))
                        Toast.makeText(context,
                            context.getString(R.string.task_created), Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context,
                            context.getString(R.string.please_enter_a_task_name), Toast.LENGTH_SHORT).show()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = stringResource(R.string.create_task),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Task Name Field (Required)
            OutlinedTextField(
                value = taskState.taskName,
                onValueChange = { viewModel.onEvent(TaskEvent.UpdateTaskName(it)) },
                label = { Text(stringResource(R.string.task_name)) },
                placeholder = { Text(stringResource(R.string.enter_task_name)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Assignment,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = taskState.taskName.isEmpty(),
                supportingText = {
                    if (taskState.taskName.isEmpty()) {
                        Text(
                            text = stringResource(R.string.required),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true
            )

            OutlinedTextField(
                value = taskState.taskDescription,
                onValueChange = { viewModel.onEvent(TaskEvent.UpdateTaskDescription(it)) },
                label = { Text(stringResource(R.string.description_optional)) },
                placeholder = { Text(stringResource(R.string.enter_task_description)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                minLines = 3,
                maxLines = 5
            )

            OutlinedTextField(
                value = taskState.taskDueDate.format(dateFormatter),
                onValueChange = { },
                label = { Text(stringResource(R.string.due_date)) },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = stringResource(R.string.select_date))
                    }
                }
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = taskState.taskPriority.name,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(R.string.priority)) },
                    leadingIcon = {
                        Icon(
                            imageVector = when(taskState.taskPriority) {
                                TaskPriority.HIGH -> Icons.Default.PriorityHigh
                                TaskPriority.MEDIUM -> Icons.Default.Warning
                                TaskPriority.LOW -> Icons.Default.LowPriority
                            },
                            contentDescription = null,
                            tint = when(taskState.taskPriority) {
                                TaskPriority.HIGH -> Color(0xFFE57373)
                                TaskPriority.MEDIUM -> Color(0xFFFFB74D)
                                TaskPriority.LOW -> Color(0xFF81C784)
                            }
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.high)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.PriorityHigh,
                                contentDescription = null,
                                tint = Color(0xFFE57373)
                            )
                        },
                        onClick = {
                            viewModel.onEvent(TaskEvent.UpdateTaskPriority(TaskPriority.HIGH))
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.medium)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFFFB74D)
                            )
                        },
                        onClick = {
                            viewModel.onEvent(TaskEvent.UpdateTaskPriority(TaskPriority.MEDIUM))
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.low)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.LowPriority,
                                contentDescription = null,
                                tint = Color(0xFF81C784)
                            )
                        },
                        onClick = {
                            viewModel.onEvent(TaskEvent.UpdateTaskPriority(TaskPriority.LOW))
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = taskState.taskDueDate.toEpochDay() * 24 * 60 * 60 * 1000
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        viewModel.onEvent(TaskEvent.UpdateTaskDueDate(selectedDate))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}



