package com.abdurashid.mutevolume.presenter.home_presenter

import android.content.Context
import com.abdurashid.mutevolume.db.DayListDao
import com.abdurashid.mutevolume.db.MyDatabase
import com.abdurashid.mutevolume.db.Today
import com.abdurashid.mutevolume.presenter.MainContract
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.Service
import java.util.*

class HomeRepository : MainContract.Model {

   lateinit var database : MyDatabase

var dao : DayListDao? = null
    override fun context(context: Context) {
//       database = Room.databaseBuilder(context, MyDatabase::class.java, "database.db").build()
//         dao = database.dao()
    }

    override fun addToDB(list: ArrayList<Today>) {
//        dao!!.addList(list)
    }



    override fun getInDB(): Single<ArrayList<Today>> {
        return dao!!.getAll().observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
    }

    override fun getTimes(service: Service): Single<JsonObject> {
        val calendar: Calendar = Calendar.getInstance()
        return service.getMonthById(
            calendar.get(Calendar.MONTH - 1),
           calendar.get(Calendar.YEAR),
            41.1,
            69.11
        ).map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}