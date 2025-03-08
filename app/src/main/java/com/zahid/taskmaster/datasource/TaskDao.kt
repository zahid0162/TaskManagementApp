package com.zahid.taskmaster.datasource

import androidx.room.*
import com.zahid.taskmaster.domain.data.TaskEntity
import com.zahid.taskmaster.domain.data.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks ORDER BY position ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Query("SELECT * FROM tasks WHERE status = :status ORDER BY position ASC")
    fun getTasksByStatus(status: TaskStatus): Flow<List<TaskEntity>>

    @Query("UPDATE tasks SET status = :status WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: String, status: TaskStatus)

    suspend fun updateTaskPositions(updates: List<TaskPositionUpdate>) {
        updates.forEach { (taskId, newPosition) ->
            updateTaskPosition(taskId, newPosition)
        }
    }

    @Query("UPDATE tasks SET position = :newPosition WHERE id = :taskId")
    suspend fun updateTaskPosition(taskId: String, newPosition: Int)

    @Query("SELECT MAX(position) FROM tasks")
    suspend fun getMaxPosition(): Int?

    @Transaction
    suspend fun updateTasksInTransaction(tasks: List<TaskEntity>) {
        tasks.forEach { task ->
            updateTask(task)
        }
    }
}

data class TaskPositionUpdate(
    val taskId: String,
    val newPosition: Int
)