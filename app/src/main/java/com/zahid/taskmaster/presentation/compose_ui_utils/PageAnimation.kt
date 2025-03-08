package com.zahid.taskmaster.presentation.compose_ui_utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


private typealias SlideDirection = AnimatedContentTransitionScope.SlideDirection
const val animDurationMillis = 500


fun NavGraphBuilder.horizontallyAnimatedComposable(
    route: String,
    argument: List<NamedNavArgument> = listOf(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        content = content,
        arguments = argument,
        enterTransition =  {
            slideIntoContainer(SlideDirection.Left, animationSpec = tween(animDurationMillis))
        },
        exitTransition = {
            slideOutOfContainer(SlideDirection.Left, animationSpec = tween(animDurationMillis))
        },
        popEnterTransition = {
            slideIntoContainer(SlideDirection.Right, animationSpec = tween(animDurationMillis))
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.Right, animationSpec = tween(animDurationMillis))
        },
    )
}



