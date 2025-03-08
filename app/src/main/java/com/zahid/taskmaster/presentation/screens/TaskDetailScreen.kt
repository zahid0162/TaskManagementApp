package com.zahid.taskmaster.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.zahid.taskmaster.R
import com.zahid.taskmaster.domain.data.Task
import com.zahid.taskmaster.domain.data.TaskStatus
import com.zahid.taskmaster.domain.enums.TaskPriority
import com.zahid.taskmaster.presentation.viewmodels.TaskEvent
import com.zahid.taskmaster.presentation.viewmodels.TaskViewModel
import com.zahid.taskmaster.utils.Constants.DateTimeFormats


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navController: NavHostController,
    viewModel: TaskViewModel,
    taskId: String
) {
    val taskState by viewModel.taskState.collectAsStateWithLifecycle()
    val task = remember(taskState.tasks) {
        taskState.tasks.find { it.id == taskId }
    }
    
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    
    LaunchedEffect(taskId) {
        viewModel.onEvent(TaskEvent.FetchAllTasks)
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.task_details)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            task?.let { currentTask ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TaskHeader(currentTask)
                    HorizontalDivider()
                    TaskDescription(currentTask)
                    HorizontalDivider()
                    TaskDetails(currentTask)
                    Spacer(modifier = Modifier.weight(1f))
                    ActionButtons(
                        task = currentTask,
                        onComplete = {
                            viewModel.onEvent(TaskEvent.UpdateTaskStatus(currentTask.id, TaskStatus.COMPLETED))
                            navController.popBackStack()
                        },
                        onDelete = { showDeleteDialog = true }
                    )
                }
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text(stringResource(R.string.delete_task)) },
                        text = { Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_task)) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.onEvent(TaskEvent.DeleteTask(currentTask))
                                    showDeleteDialog = false
                                    navController.popBackStack()
                                }
                            ) {
                                Text(stringResource(R.string.delete))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text(stringResource(R.string.cancel))
                            }
                        }
                    )
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.task_not_found))
                }
            }
        }
    }
}

@Composable
private fun TaskHeader(task: Task) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            PriorityBadge(priority = task.priority)
        }
        
        AnimatedVisibility(
            visible = task.status == TaskStatus.COMPLETED,
            enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessLow)),
            exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessLow))
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.completed),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun PriorityBadge(priority: TaskPriority) {
    val (backgroundColor, textColor) = when (priority) {
        TaskPriority.HIGH -> Pair(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.error
        )
        TaskPriority.MEDIUM -> Pair(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.tertiary
        )
        TaskPriority.LOW -> Pair(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary
        )
    }
    
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = when (priority) {
                    TaskPriority.HIGH -> Icons.Default.PriorityHigh
                    TaskPriority.MEDIUM -> Icons.Default.Warning
                    TaskPriority.LOW -> Icons.Default.LowPriority
                },
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = priority.name,
                style = MaterialTheme.typography.labelMedium,
                color = textColor
            )
        }
    }
}

@Composable
private fun TaskDescription(task: Task) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = task.description.ifEmpty { stringResource(R.string.no_description_provided) },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun TaskDetails(task: Task) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.details),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        DetailItem(
            icon = Icons.Default.DateRange,
            label = stringResource(R.string.due_date),
            value = task.dateTime.format(DateTimeFormats.DATE_FORMATTER)
        )
        
        DetailItem(
            icon = Icons.Default.Schedule,
            label = stringResource(R.string.due_time),
            value = task.dateTime.format(DateTimeFormats.TIME_FORMATTER)
        )
        
        DetailItem(
            icon = Icons.Default.Info,
            label = stringResource(R.string.status),
            value = task.status.name,
            valueColor = when (task.status) {
                TaskStatus.COMPLETED -> MaterialTheme.colorScheme.primary
                TaskStatus.PENDING -> MaterialTheme.colorScheme.tertiary
            }
        )
    }
}

@Composable
private fun DetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = valueColor
            )
        }
    }
}

@Composable
private fun ActionButtons(
    task: Task,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(
            onClick = onDelete,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Task"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Delete")
        }
        if (task.status != TaskStatus.COMPLETED) {
            Button(
                onClick = onComplete,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.mark_complete)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.complete))
            }
        } else {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                enabled = false,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.already_completed)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.completed))
            }
        }
    }
} 