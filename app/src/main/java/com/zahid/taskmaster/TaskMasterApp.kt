package com.zahid.taskmaster

import android.app.Application
import android.content.Context
import com.zahid.taskmaster.di.coroutineDispatchersModule
import com.zahid.taskmaster.di.databaseModule
import com.zahid.taskmaster.di.repositoryModule
import com.zahid.taskmaster.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskMasterApp : Application() {
    //region Properties
    companion object {
        lateinit var appInstance: TaskMasterApp
        fun getApplication(): TaskMasterApp = appInstance
        fun getContext(): Context = appInstance.applicationContext
    }
    //endregion

    //region LifeCycle
    override fun onCreate() {
        appInstance = this
        super.onCreate()
        startKoin {
            androidLogger()
            // Reference Android context
            androidContext(this@TaskMasterApp)
            modules(
                coroutineDispatchersModule,
                viewModelModule,
                databaseModule,
                repositoryModule
            )
        }
    }
    //endregion
}