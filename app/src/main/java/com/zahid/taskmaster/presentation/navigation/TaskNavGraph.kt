package com.zahid.taskmaster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zahid.taskmaster.presentation.compose_ui_utils.expandableComposable
import com.zahid.taskmaster.presentation.compose_ui_utils.horizontallyAnimatedComposable
import com.zahid.taskmaster.presentation.screens.CreateTaskScreen
import com.zahid.taskmaster.presentation.screens.TaskListScreen
import com.zahid.taskmaster.presentation.screens.TaskDetailScreen
import com.zahid.taskmaster.presentation.viewmodels.TaskViewModel
import org.koin.androidx.compose.koinViewModel

enum class TaskScreenRoutes(val route: String) {
    TASK_LIST("task_list"),
    CREATE_TASK("create_task"),
    TASK_DETAIL("task_detail"),
}

@Composable
fun TasksNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = RootNavRoutes.USER_HOME.route,
        startDestination = TaskScreenRoutes.TASK_LIST.route
    ) {
        horizontallyAnimatedComposable(TaskScreenRoutes.TASK_LIST.route) {
            val viewModel = koinViewModel<TaskViewModel>()
            TaskListScreen(navController, viewModel)
        }
        
        expandableComposable(
            route = TaskScreenRoutes.CREATE_TASK.route,
            navController = navController
        ) {
            val viewModel = koinViewModel<TaskViewModel>()
            CreateTaskScreen(navController, viewModel)
        }
        
        horizontallyAnimatedComposable(
            route = "${TaskScreenRoutes.TASK_DETAIL.route}/{taskId}"
        ) { backStackEntry ->
            val viewModel = koinViewModel<TaskViewModel>()
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            TaskDetailScreen(navController, viewModel, taskId)
        }
    }
}