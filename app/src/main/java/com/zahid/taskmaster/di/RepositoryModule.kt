package com.zahid.taskmaster.di

import com.zahid.taskmaster.datasource.TaskRepositoryImpl
import com.zahid.taskmaster.domain.repository.TaskRepository
import org.koin.dsl.module

val repositoryModule = module {
    single <TaskRepository> { TaskRepositoryImpl(get()) }
}