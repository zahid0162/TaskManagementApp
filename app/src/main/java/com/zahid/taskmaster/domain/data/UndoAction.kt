package com.zahid.taskmaster.domain.data

sealed class UndoAction {
    data class DeleteTask(val task: Task) : UndoAction()
    data class CompleteTask(
        val taskId: String,
        val previousStatus: TaskStatus
    ) : UndoAction()
    data class ReorderTasks(
        val tasks: List<Task>,
        val previousOrder: List<Task>
    ) : UndoAction()
}
