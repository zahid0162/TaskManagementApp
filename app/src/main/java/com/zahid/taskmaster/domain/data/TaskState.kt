package com.zahid.taskmaster.domain.data

import com.zahid.taskmaster.domain.enums.TaskPriority
import java.time.LocalDate

data class TaskState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val error: String? = null,
    val snackbarMessage: String? = null,
    val showUndoButton: Boolean = false,
    
    // Form fields
    val taskName: String = "",
    val taskDescription: String = "",
    val taskPriority: TaskPriority = TaskPriority.LOW,
    val taskDueDate: LocalDate = LocalDate.now()
)