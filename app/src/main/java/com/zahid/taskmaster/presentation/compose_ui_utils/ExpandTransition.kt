package com.zahid.taskmaster.presentation.compose_ui_utils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.expandableComposable(
    route: String,
    navController: NavHostController,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            scaleIn(
                initialScale = 0.0f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            scaleOut(
                targetScale = 0.0f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(500))
        },
        popEnterTransition = {
            scaleIn(
                initialScale = 0.0f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(500))
        },
        popExitTransition = {
            scaleOut(
                targetScale = 0.0f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(500))
        }
    ) {
        content(it)
    }
} 