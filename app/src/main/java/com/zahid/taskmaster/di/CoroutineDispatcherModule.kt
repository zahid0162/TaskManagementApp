package com.zahid.taskmaster.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module


enum class CoroutineDispatcherModuleEnums(val dispatcherName:String){
    DEFAULT("defaultDispatcher"),
    IO("ioDispatcher"),
    MAIN("mainDispatcher"),
    MAIN_IMMEDIATE("mainImmediateDispatcher"),
}
private fun provideDefaultDispatcher() : CoroutineDispatcher = Dispatchers.Default
private fun provideIODispatcher() : CoroutineDispatcher = Dispatchers.IO
private fun provideMainDispatcher() : CoroutineDispatcher = Dispatchers.Main
private fun provideMainImmediateDispatcher() : CoroutineDispatcher = Dispatchers.Main.immediate

val coroutineDispatchersModule= module {
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.DEFAULT.dispatcherName)) { provideDefaultDispatcher() }
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.IO.dispatcherName)) { provideIODispatcher() }
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.MAIN.dispatcherName)) { provideMainDispatcher() }
    single<CoroutineDispatcher>(named(CoroutineDispatcherModuleEnums.MAIN_IMMEDIATE.dispatcherName)) { provideMainImmediateDispatcher() }
}