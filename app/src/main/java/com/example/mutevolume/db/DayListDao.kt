package com.example.mutevolume.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single


@Dao
interface DayListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(list: ArrayList<Today>)

    @Query("select * from Today")
    fun getAll() : Single<ArrayList<Today>>


}