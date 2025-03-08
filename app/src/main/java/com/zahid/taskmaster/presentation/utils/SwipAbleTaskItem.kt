package com.zahid.taskmaster.presentation.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zahid.taskmaster.domain.data.Task

@Composable
fun SwipeableTaskItem(
    task: Task,
    onComplete: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDragging: Boolean = false,
    dragModifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { totalDistance -> totalDistance * 0.5f },
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onComplete()
                    true
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onDelete()
                    true
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )

    // Add a pulsating animation to the drag handle when not dragging
    val infiniteTransition = rememberInfiniteTransition(label = "dragIndicatorPulse")
    val pulseAlpha = if (!isDragging) {
        infiniteTransition.animateFloat(
            initialValue = 0.6f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseAlpha"
        ).value
    } else {
        1.0f
    }

    // Enhanced drag modifier with visual feedback
    val enhancedDragModifier = dragModifier.then(
        Modifier.alpha(pulseAlpha)
    )

    SwipeToDismissBox(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        state = dismissState,
        backgroundContent = { SwipeBackground(dismissState) },
        content = {
            TaskItem(
                task = task,
                onClick = onClick,
                modifier = modifier,
                isDragging = isDragging,
                dragModifier = enhancedDragModifier
            )
        },
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = true
    )
}

@Composable
private fun SwipeBackground(dismissState: SwipeToDismissBoxState) {
    val direction = dismissState.dismissDirection ?: return

    val completeColor = Color(0xFF4CAF50) // Pleasant green
    val deleteColor = Color(0xFFE57373)   // Pleasant light red
    
    val color by animateColorAsState(
        targetValue = when (direction) {
            SwipeToDismissBoxValue.StartToEnd -> completeColor
            SwipeToDismissBoxValue.EndToStart -> deleteColor
            SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.background
        },
        label = "backgroundColor"
    )

    val alignment = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        SwipeToDismissBoxValue.Settled -> Alignment.Center
    }

    val icon = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> Icons.Default.CheckCircle
        SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
        SwipeToDismissBoxValue.Settled -> Icons.Default.CheckCircle
    }

    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f,
        label = "iconScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.scale(scale),
            tint = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primaryContainer
                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.background
            },
        )
    }
}