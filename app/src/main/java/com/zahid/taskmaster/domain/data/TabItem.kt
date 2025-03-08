package com.zahid.taskmaster.domain.data

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: String,
    val status: TaskStatus?,
    val icon: ImageVector
)