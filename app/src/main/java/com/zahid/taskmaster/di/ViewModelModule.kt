package com.zahid.taskmaster.di

import com.zahid.taskmaster.presentation.viewmodels.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        TaskViewModel(
            get(named(CoroutineDispatcherModuleEnums.IO.dispatcherName)),
            get(),

        )
    }
}