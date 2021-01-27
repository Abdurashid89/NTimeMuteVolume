package com.example.mutevolume.presenter.home_presenter

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mutevolume.db.DayListDao
import com.example.mutevolume.db.MyDatabase
import com.example.mutevolume.db.Today
import com.example.mutevolume.presenter.MainContract
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class HomeRepository : MainContract.Model {

   lateinit var database : MyDatabase

var dao : DayListDao? = null
    override fun context(context: Context) {
       database = Room.databaseBuilder(context, MyDatabase::class.java, "database").build()
         dao = database.dao()
    }

    override fun addToDB(list: ArrayList<Today>) {
        dao!!.addList(list)
    }

    override fun getInDB(): Single<ArrayList<Today>> {
        return dao!!.getAll().observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
    }


}