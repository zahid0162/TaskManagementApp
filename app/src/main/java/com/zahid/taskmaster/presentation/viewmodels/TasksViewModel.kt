package com.zahid.taskmaster.presentation.viewmodels

import com.zahid.taskmaster.domain.data.Task
import com.zahid.taskmaster.domain.data.TaskStatus
import com.zahid.taskmaster.domain.repository.TaskRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahid.taskmaster.domain.data.TaskState
import com.zahid.taskmaster.domain.data.UndoAction
import com.zahid.taskmaster.domain.enums.SortOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.zahid.taskmaster.domain.enums.TaskPriority
import java.time.LocalDate
import android.util.Log
import com.zahid.taskmaster.utils.Constants.SnackbarMessages


class TaskViewModel (
    private val ioDispatcher: CoroutineDispatcher,
    private val repository: TaskRepository,
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())
    val taskState: StateFlow<TaskState> = _taskState.asStateFlow()
    private val _uiEvents = Channel<TaskUiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()
    private var lastDeletedTask: Task? = null
    private var lastUndoAction: UndoAction? = null
    private var _isReordering = false

    init {
        fetchAllTasks()
    }

    private fun emitUiEvents(uiEvents: TaskUiEvents) {
        viewModelScope.launch(ioDispatcher) {
            _uiEvents.send(uiEvents)
        }
    }

    private fun showSnackbar(message: String, showUndo: Boolean = false) {
        emitUiEvents(TaskUiEvents.ShowSnackBar(message, showUndo))
    }

    private fun clearSnackbar() {
        emitUiEvents(TaskUiEvents.ClearSnackBar)
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.AddTask -> addTask(event.task)
            is TaskEvent.UpdateTask -> updateTask(event.task)
            is TaskEvent.DeleteTask -> deleteTask(event.task)
            is TaskEvent.FetchTasksByStatus -> fetchTasksByStatus(event.status)
            is TaskEvent.FetchAllTasks -> fetchAllTasks()
            is TaskEvent.UpdateTaskStatus -> updateTaskStatus(event.taskId, event.status)
            is TaskEvent.SortTasks -> sortTasks(event.sortOrder)
            is TaskEvent.ReorderTasks -> reorderTasks(event.fromIndex, event.toIndex)
            is TaskEvent.UndoDeleteTask -> undoDeleteTask()
            TaskEvent.ClearSnackbar -> clearSnackbar()

            is TaskEvent.UpdateTaskName -> updateTaskName(event.name)
            is TaskEvent.UpdateTaskDescription -> updateTaskDescription(event.description)
            is TaskEvent.UpdateTaskPriority -> updateTaskPriority(event.priority)
            is TaskEvent.UpdateTaskDueDate -> updateTaskDueDate(event.dueDate)
            TaskEvent.ResetTaskForm -> resetTaskForm()
            is TaskEvent.ReorderTasksPreview -> reorderTasksPreview(event.fromIndex, event.toIndex)
        }
    }

    private fun fetchAllTasks() {
        if (_isReordering) return
        
        viewModelScope.launch(ioDispatcher) {
            repository.getAllTasks().collect { tasks ->
                _taskState.update { it.copy(tasks = tasks, isLoading = false) }
            }
        }
    }

    private fun fetchTasksByStatus(status: TaskStatus) {
        viewModelScope.launch(ioDispatcher) {
            repository.getTasksByStatus(status)
                .onStart { _taskState.value = _taskState.value.copy(isLoading = true) }
                .catch { e -> _taskState.value = _taskState.value.copy(isLoading = false, error = e.message) }
                .collect { tasks -> _taskState.value = TaskState(isLoading = false, tasks = tasks) }
        }
    }

    private fun addTask(task: Task) {
        viewModelScope.launch(ioDispatcher) {
            repository.addTask(task)
            fetchAllTasks()
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch(ioDispatcher) {
            repository.updateTask(task)
            fetchAllTasks()
        }
    }

    private fun deleteTask(task: Task) {
        lastDeletedTask = task
        lastUndoAction = UndoAction.DeleteTask(task)
        viewModelScope.launch(ioDispatcher) {
            repository.deleteTask(task)
            fetchAllTasks()
            showSnackbar(SnackbarMessages.TASK_DELETED, showUndo = true)
        }
    }

    private fun updateTaskStatus(taskId: String, status: TaskStatus) {
        viewModelScope.launch(ioDispatcher) {
            repository.updateTaskStatus(taskId, status)
            fetchAllTasks()
        }
    }

    private fun sortTasks(sortOrder: SortOrder) {
        val currentTasks = _taskState.value.tasks
        val sortedTasks = when (sortOrder) {
            SortOrder.PRIORITY -> currentTasks.sortedByDescending { it.priority }
            SortOrder.DUE_DATE -> currentTasks.sortedBy { it.dateTime }
            SortOrder.ALPHABETICAL -> currentTasks.sortedBy { it.name }
        }
        _taskState.value = _taskState.value.copy(tasks = sortedTasks)
    }

    private fun reorderTasks(fromIndex: Int, toIndex: Int) {
        if (fromIndex == toIndex) return
        
        _isReordering = true
        
        val currentTasks = _taskState.value.tasks.toMutableList()
        if (fromIndex < currentTasks.size && toIndex < currentTasks.size) {
            // The UI is already updated from the preview, so we just need to
            // update the positions and save to the database
            
            // Update positions for all tasks to match their current order
            val updatedTasks = currentTasks.mapIndexed { index, task -> 
                task.copy(position = index) 
            }
            
            // Save the final state to the database
            viewModelScope.launch(ioDispatcher) {
                try {
                    repository.updateTasksOrder(updatedTasks)
                } catch (e: Exception) {
                    Log.e("TasksViewModel", "Error updating task order: ${e.message}")
                    // On error, refresh the list to get the correct order
                    fetchAllTasks()
                } finally {
                    _isReordering = false
                }
            }
        } else {
            _isReordering = false
        }
    }

    private fun reorderTasksPreview(fromIndex: Int, toIndex: Int) {
        if (fromIndex == toIndex) return
        
        val currentTasks = _taskState.value.tasks.toMutableList()
        if (fromIndex < currentTasks.size && toIndex < currentTasks.size) {
            val task = currentTasks.removeAt(fromIndex)
            currentTasks.add(toIndex, task)
            
            // Only update the UI state, don't change positions yet
            _taskState.value = _taskState.value.copy(tasks = currentTasks)
        }
    }

    private fun undoDeleteTask() {
        lastDeletedTask?.let { task ->
            viewModelScope.launch(ioDispatcher) {
                repository.addTask(task)
                lastDeletedTask = null
                fetchAllTasks()
                showSnackbar(SnackbarMessages.TASK_RESTORED)
            }
        }
    }

    private fun updateTaskName(name: String) {
        _taskState.update { it.copy(taskName = name) }
    }

    private fun updateTaskDescription(description: String) {
        _taskState.update { it.copy(taskDescription = description) }
    }

    private fun updateTaskPriority(priority: TaskPriority) {
        _taskState.update { it.copy(taskPriority = priority) }
    }

    private fun updateTaskDueDate(dueDate: LocalDate) {
        _taskState.update { it.copy(taskDueDate = dueDate) }
    }

    private fun resetTaskForm() {
        _taskState.update { it.copy(
            taskName = "",
            taskDescription = "",
            taskPriority = TaskPriority.LOW,
            taskDueDate = LocalDate.now()
        ) }
    }
}