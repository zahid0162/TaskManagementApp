package com.zahid.taskmaster.presentation.viewmodels

sealed class TaskUiEvents {
    data class ShowSnackBar(val message: String, val showUndo: Boolean) : TaskUiEvents()
    data object ClearSnackBar:TaskUiEvents()
}