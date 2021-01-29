package com.example.mutevolume.presenter.home_presenter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.mutevolume.db.DayListDao
import com.example.mutevolume.db.MyDatabase
import com.example.mutevolume.db.Today
import com.example.mutevolume.net.NetworkManager
import com.example.mutevolume.presenter.MainContract
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class HomePresenter(
    val context: Context,
    val view: MainContract.View,
    private val model: MainContract.Model
) :
    MainContract.Presenter {
    var dao: DayListDao? = null
    var db: MyDatabase? = null
    private val networkManager = NetworkManager(context)
    private val list = ArrayList<Today>()

    @SuppressLint("CheckResult")
    override fun addData(data: JsonObject) {


        data["data"].asJsonArray.forEach { it ->
            val fajr = it.asJsonObject["timings"].asJsonObject["Fajr"].toString().substring(1, 6)
            val sunrise =
                it.asJsonObject["timings"].asJsonObject["Sunrise"].toString().substring(1, 6)
            val dhuhr = it.asJsonObject["timings"].asJsonObject["Dhuhr"].toString().substring(1, 6)
            val asr = it.asJsonObject["timings"].asJsonObject["Asr"].toString().substring(1, 6)
            val magrib =
                it.asJsonObject["timings"].asJsonObject["Maghrib"].toString().substring(1, 6)
            val isha = it.asJsonObject["timings"].asJsonObject["Isha"].toString().substring(1, 6)

            list.add(Today(fajr, sunrise, dhuhr, asr, magrib, isha))
            Log.d(TAG, fajr)

           /* model.context(context = context)
            if (list.isNotEmpty()) model.addToDB(list)

            if (!networkManager.isConnected()) {
                model.getInDB().doOnSuccess { }
                    .doAfterTerminate { }
                    .map { view.addItem(it) }.observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io()).subscribe()
            }*/
        }
        view.addItem(list)

        /*db!!.dao().getAll().observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { view.addItem(it) }*/
    }


    companion object {
        private const val TAG = "HomePresenter"
    }
}