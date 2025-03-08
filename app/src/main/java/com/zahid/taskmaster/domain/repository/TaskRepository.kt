package com.zahid.taskmaster.domain.repository


import com.zahid.taskmaster.domain.data.Task
import com.zahid.taskmaster.domain.data.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByStatus(status: TaskStatus): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    suspend fun updateTaskStatus(taskId: String, status: TaskStatus)
    suspend fun updateTasksOrder(tasks: List<Task>)
    suspend fun getNextPosition(): Int
}