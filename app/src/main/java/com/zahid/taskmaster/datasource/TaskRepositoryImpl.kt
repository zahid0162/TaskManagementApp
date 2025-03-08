package com.zahid.taskmaster.datasource


import com.zahid.taskmaster.domain.data.Task
import com.zahid.taskmaster.domain.data.TaskEntity
import com.zahid.taskmaster.domain.data.TaskStatus
import com.zahid.taskmaster.domain.enums.TaskPriority
import com.zahid.taskmaster.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { it.map { entity -> entity.toDomain() } }
    }

    override fun getTasksByStatus(status: TaskStatus): Flow<List<Task>> {
        return taskDao.getTasksByStatus(status).map { it.map { entity -> entity.toDomain() } }
    }

    override suspend fun getTaskById(id: String): Task? {
        return taskDao.getTaskById(id)?.toDomain()
    }

    override suspend fun updateTaskStatus(taskId: String, status: TaskStatus) {
        taskDao.updateTaskStatus(taskId, status)
    }

    override suspend fun updateTasksOrder(tasks: List<Task>) {
        taskDao.updateTasksInTransaction(tasks.map { it.toEntity() })
    }

    override suspend fun getNextPosition(): Int {
        return (taskDao.getMaxPosition() ?: -1) + 1
    }


    private fun Task.toEntity() = TaskEntity(
        id,
        name,
        description,
        priority.name,
        dateTime,
        status = status.name,
        position = position
    )

    private fun TaskEntity.toDomain() = Task(
        id,
        name,
        description,
        TaskPriority.getPriorityByName(priority),
        dateTime,
        status = TaskStatus.getStatusByName(status),
        position = position
    )
}