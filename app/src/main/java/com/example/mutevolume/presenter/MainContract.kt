package com.example.mutevolume.presenter

import android.content.Context
import com.example.mutevolume.db.Today
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.Service
import java.util.*

interface MainContract {

    interface Model {

        fun context(context: Context)

        fun addToDB(list: ArrayList<Today>)

        fun getInDB(): Single<ArrayList<Today>>
        fun getTimes(service: Service): Single<JsonObject> {

            val calendar: Calendar = Calendar.getInstance()
            return service.getMonthById(
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR),
                41.1,
                69.11
            ).map { it }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    }

    interface View {

        fun addItem(list: ArrayList<Today>)
        fun showProgress(isShow: Boolean)
        fun showMessage(message: String)
    }

    interface Presenter

}