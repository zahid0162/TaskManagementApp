package com.zahid.taskmaster.presentation.utils

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zahid.taskmaster.R
import com.zahid.taskmaster.domain.data.Task
import com.zahid.taskmaster.domain.data.TaskStatus
import com.zahid.taskmaster.domain.enums.TaskPriority
import com.zahid.taskmaster.utils.Constants.DateTimeFormats.DISPLAY_FORMAT_DATE


@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDragging: Boolean = false,
    dragModifier: Modifier = Modifier
) {

    val formattedDate = task.dateTime.format(DISPLAY_FORMAT_DATE)
    
    // Enhanced elevation and scaling for better visual feedback
    val elevation = if (isDragging) 6.dp else 2.dp
    val scale = if (isDragging) 1.03f else 1.0f
    
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .scale(scale)
            .animateContentSize(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isDragging) 
                MaterialTheme.colorScheme.surfaceVariant 
            else 
                MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Due: $formattedDate",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssignedChip(task.priority)
                    StatusChip(task.status)
                }
            }
            Icon(
                imageVector = Icons.Default.DragIndicator,
                contentDescription = stringResource(R.string.drag_to_reorder),
                modifier = dragModifier
                    .padding(start = 8.dp)
                    .size(24.dp),
                tint = if (isDragging)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun AssignedChip(priority: TaskPriority) {
    AssistChip(
        onClick = { },
        label = { Text(priority.name) },
        colors = AssistChipDefaults.assistChipColors(
            labelColor = when (priority) {
                TaskPriority.HIGH -> MaterialTheme.colorScheme.error
                TaskPriority.MEDIUM -> MaterialTheme.colorScheme.tertiary
                TaskPriority.LOW -> MaterialTheme.colorScheme.primary
            },
            containerColor = when (priority) {
                TaskPriority.HIGH -> MaterialTheme.colorScheme.errorContainer
                TaskPriority.MEDIUM -> MaterialTheme.colorScheme.tertiaryContainer
                TaskPriority.LOW -> MaterialTheme.colorScheme.primaryContainer
            }
        )
    )
}

@Composable
private fun StatusChip(status: TaskStatus) {
    AssistChip(
        onClick = { },
        label = { Text(status.name) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = when (status) {
                TaskStatus.COMPLETED -> MaterialTheme.colorScheme.primaryContainer
                TaskStatus.PENDING -> MaterialTheme.colorScheme.secondaryContainer
            }
        )
    )
}


