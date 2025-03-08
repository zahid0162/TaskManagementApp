package com.zahid.taskmaster.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.rememberNavController
import com.zahid.taskmaster.presentation.navigation.TasksNavGraph
import com.zahid.taskmaster.presentation.theme.TaskMasterAppColors


@Composable
fun MainTasksScreen() {
    val navController = rememberNavController()

    Surface(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TasksNavGraph(navController)
        }
    }

}