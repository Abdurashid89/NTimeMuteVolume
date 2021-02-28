package com.abdurashid.mutevolume.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Today::class], version = 1)
abstract class MyDatabase : RoomDatabase(){
    abstract fun dao(): DayListDao
}