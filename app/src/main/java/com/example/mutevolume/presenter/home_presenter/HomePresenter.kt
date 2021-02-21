package com.example.mutevolume.presenter.home_presenter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.mutevolume.db.Today
import com.example.mutevolume.net.NetworkManager
import com.example.mutevolume.presenter.MainContract
import com.google.gson.JsonObject
import net.Service

class HomePresenter(
    val context: Context,
    val view: MainContract.View,
    private val model: MainContract.Model
) :
    MainContract.Presenter {
    private val networkManager = NetworkManager(context)
    private val list = ArrayList<Today>()


    @SuppressLint("CheckResult")
    fun netWork(service: Service) {
        if (networkManager.isConnected()) {

            model.getTimes(service)
                .doOnSuccess { view.showProgress(true) }
                .doAfterTerminate { view.showProgress(false) }
                .subscribe({
                    addToList(it)
                }, {
                    view.showMessage(it.message!!)
                })
        } else {
            view.showMessage("please check your internet connection")
        }
    }

    private fun addToList(data: JsonObject) {
        data["data"].asJsonArray.forEach {
            val fajr = it.asJsonObject["timings"].asJsonObject["Fajr"].toString().substring(1, 6)
            val sunrise =
                it.asJsonObject["timings"].asJsonObject["Sunrise"].toString().substring(1, 6)
            val dhuhr = it.asJsonObject["timings"].asJsonObject["Dhuhr"].toString().substring(1, 6)
            val asr = it.asJsonObject["timings"].asJsonObject["Asr"].toString().substring(1, 6)
            val magrib =
                it.asJsonObject["timings"].asJsonObject["Maghrib"].toString().substring(1, 6)
            val isha = it.asJsonObject["timings"].asJsonObject["Isha"].toString().substring(1, 6)

            val time = it.asJsonObject["timings"].asJsonObject["Fajr"].toString()

            list.add(Today(fajr, sunrise, dhuhr, asr, magrib, isha))
            Log.d(TAG, time)
        }
        view.addItem(list)
    }

    companion object {
        private const val TAG = "HomePresenter"
    }
}