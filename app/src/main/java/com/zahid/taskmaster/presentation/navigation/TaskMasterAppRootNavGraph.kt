package com.zahid.taskmaster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zahid.taskmaster.presentation.compose_ui_utils.navigateToNextScreen
import com.zahid.taskmaster.presentation.screens.MainTasksScreen

enum class RootNavRoutes(val route: String) {
    Splash("splash"),
    USER_HOME("user-home"),

}

@Composable
fun AppNavHostGraph(
    navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = RootNavRoutes.Splash.route
    ) {
        splashNavGraph(navController)
        composable(RootNavRoutes.USER_HOME.route){
            MainTasksScreen()
        }
    }

}
