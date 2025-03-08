package com.zahid.taskmaster.presentation.viewmodels

import com.zahid.taskmaster.domain.data.Task
import com.zahid.taskmaster.domain.data.TaskStatus
import com.zahid.taskmaster.domain.enums.SortOrder
import com.zahid.taskmaster.domain.enums.TaskPriority
import java.time.LocalDate

sealed class TaskEvent {
    data class AddTask(val task: Task) : TaskEvent()
    data class UpdateTask(val task: Task) : TaskEvent()
    data class DeleteTask(val task: Task) : TaskEvent()
    data class FetchTasksByStatus(val status: TaskStatus) : TaskEvent()
    data class SortTasks(val sortOrder: SortOrder) : TaskEvent()
    data object FetchAllTasks : TaskEvent()
    data class UpdateTaskStatus(val taskId: String, val status: TaskStatus) : TaskEvent()
    data class ReorderTasks(val fromIndex: Int, val toIndex: Int) : TaskEvent()
    data object UndoDeleteTask : TaskEvent()
    data object ClearSnackbar : TaskEvent()
    
    // Form field events
    data class UpdateTaskName(val name: String) : TaskEvent()
    data class UpdateTaskDescription(val description: String) : TaskEvent()
    data class UpdateTaskPriority(val priority: TaskPriority) : TaskEvent()
    data class UpdateTaskDueDate(val dueDate: LocalDate) : TaskEvent()
    data object ResetTaskForm : TaskEvent()

    // Add this new event for preview reordering (UI only)
    data class ReorderTasksPreview(val fromIndex: Int, val toIndex: Int) : TaskEvent()
}