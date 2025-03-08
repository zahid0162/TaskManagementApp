package com.zahid.taskmaster.datasource


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zahid.taskmaster.domain.data.TaskEntity
import com.zahid.taskmaster.utils.Converters

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}