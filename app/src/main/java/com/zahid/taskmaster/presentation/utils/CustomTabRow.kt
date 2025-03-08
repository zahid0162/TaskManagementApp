package com.zahid.taskmaster.presentation.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Pending
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zahid.taskmaster.domain.data.TabItem
import com.zahid.taskmaster.domain.data.TaskStatus

@Composable
fun CustomTabRow(
    selectedStatus: TaskStatus?,
    onStatusSelected: (TaskStatus?) -> Unit
) {
    val tabs = remember {
        listOf(
            TabItem("All", null, Icons.Rounded.List),
            TabItem("Pending", TaskStatus.PENDING, Icons.Rounded.Pending),
            TabItem("Completed", TaskStatus.COMPLETED, Icons.Rounded.CheckCircle)
        )
    }

    val selectedTabIndex = remember(selectedStatus) {
        tabs.indexOfFirst { it.status == selectedStatus }.takeIf { it != -1 } ?: 0
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        height = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = index == selectedTabIndex
                val contentColor by animateColorAsState(
                    targetValue = if (selected) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    label = "Tab color animation"
                )
                
                Tab(
                    selected = selected,
                    onClick = { onStatusSelected(tab.status) },
                    text = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            ),
                            color = contentColor
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}
