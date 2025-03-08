package com.zahid.taskmaster.presentation.compose_ui_utils

import androidx.navigation.NavHostController

fun <T> NavHostController.navigateToNextScreen(route: String, list: List<Pair<String, T?>> = arrayListOf(), shouldPopBackStack: Boolean = true) {
    list.forEach {
        currentBackStackEntry?.savedStateHandle?.apply {
            set(it.first, it.second)
        }
    }
    if (shouldPopBackStack) popBackStack()
    navigate(route)
}

fun <T> NavHostController.navigateToBackWithResult(list: List<Pair<String, T?>> = arrayListOf()) {
    popBackStack()
    list.forEach {
        currentBackStackEntry?.savedStateHandle?.apply {
            set(it.first, it.second)
        }
    }
}