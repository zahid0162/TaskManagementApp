package com.zahid.taskmaster.domain.enums

enum class TaskPriority {
    HIGH,
    MEDIUM,
    LOW;

    companion object {
        fun getPriorityByName(name: String): TaskPriority {
            return when (name) {
                "HIGH" -> HIGH
                "MEDIUM" -> MEDIUM
                "LOW" -> LOW
                else -> {
                    LOW
                }
            }
        }
    }
}