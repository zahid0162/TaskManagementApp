package com.zahid.taskmaster.domain.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zahid.taskmaster.domain.enums.TaskPriority
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val priority: String,
    val dateTime: LocalDateTime,
    val position: Int,
    val status: String
) {
    fun toDomain(): Task {
        return Task(
            id = id,
            name = name,
            description = description,
            priority = TaskPriority.getPriorityByName(priority),
            dateTime = dateTime,
            position = position,
            status = TaskStatus.getStatusByName(status)
        )
    }
}
