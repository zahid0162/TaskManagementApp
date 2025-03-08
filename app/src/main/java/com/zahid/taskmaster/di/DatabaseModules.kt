package com.zahid.taskmaster.di

import android.app.Application
import androidx.room.Room
import com.zahid.taskmaster.datasource.TaskDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Application>(),
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    single { get<TaskDatabase>().taskDao() }
}