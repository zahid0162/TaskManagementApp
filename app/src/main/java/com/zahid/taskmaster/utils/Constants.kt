package com.zahid.taskmaster.utils

import java.time.format.DateTimeFormatter

object Constants {
    // Snackbar messages
    object SnackbarMessages {
        const val TASK_DELETED = "Task deleted"
        const val TASK_RESTORED = "Task restored"

    }
    
    // Date and time formats
    object DateTimeFormats {
        val DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        val TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a")
        val DISPLAY_FORMAT_DATE = DateTimeFormatter.ofPattern("MMM d, yyyy")
    }

}