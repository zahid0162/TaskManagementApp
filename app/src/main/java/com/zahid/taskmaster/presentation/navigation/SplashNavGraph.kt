package com.zahid.taskmaster.presentation.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.zahid.taskmaster.presentation.compose_ui_utils.horizontallyAnimatedComposable
import com.zahid.taskmaster.presentation.screens.SplashScreen

enum class SplashScreenRoutes(val route: String) {
    SPLASH("splash_screen"),
}


fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {
    navigation(
        startDestination = SplashScreenRoutes.SPLASH.route,
        route = RootNavRoutes.Splash.route,
    ) {
        horizontallyAnimatedComposable(SplashScreenRoutes.SPLASH.route) {
            SplashScreen(navHostController = navController)
        }
    }
}