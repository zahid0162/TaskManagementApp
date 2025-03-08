package com.zahid.taskmaster.domain.data

enum class TaskStatus {
    PENDING, COMPLETED;


    companion object {
        fun getStatusByName(name: String): TaskStatus {
            return when (name) {
                "PENDING" -> PENDING
                "COMPLETED" -> COMPLETED
                else -> {
                    PENDING
                }
            }
        }
    }
}

