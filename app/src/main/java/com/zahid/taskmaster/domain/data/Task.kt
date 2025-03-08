package com.zahid.taskmaster.domain.data

import com.zahid.taskmaster.domain.enums.TaskPriority
import java.time.LocalDateTime
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val priority: TaskPriority,
    val dateTime: LocalDateTime,
    val position: Int,
    val status: TaskStatus = TaskStatus.PENDING
) {
    fun toEntity(): TaskEntity {
        return TaskEntity(
            id = id,
            name = name,
            description = description,
            priority = priority.name,
            dateTime = dateTime,
            position = position,
            status = status.name
        )
    }
}

