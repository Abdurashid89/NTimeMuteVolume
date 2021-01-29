package com.example.mutevolume.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Today::class], version = 1)
abstract class MyDatabase : RoomDatabase(), DayListDao {
    abstract fun dao(): DayListDao
}