package com.example.mutevolume.presenter.home_presenter

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mutevolume.db.Today
import com.example.mutevolume.net.NetworkManager
import com.example.mutevolume.presenter.MainContract
import com.example.mutevolume.receiver.MyReceiver
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class HomePresenter(
    val context: Context,
    val view: MainContract.View,
    val model: MainContract.Model
) :
    MainContract.Presenter {
    val networkManager = NetworkManager(context)

    @SuppressLint("CheckResult")
    override fun addData(data: JsonObject) {
        val list = ArrayList<Today>()

        data["data"].asJsonArray.forEach {
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
        }


        /* model.context(context = context)
         if (list.isNotEmpty()) model.addToDB(list)*/

        /*  if (!networkManager.isConnected()) {
              model.getInDB().map { view.addItem(it) }.observeOn(Schedulers.io())
                  .subscribeOn(AndroidSchedulers.mainThread())
          }*/

        view.addItem(list)
    }

    companion object {
        private const val TAG = "HomePresenter"
    }
}