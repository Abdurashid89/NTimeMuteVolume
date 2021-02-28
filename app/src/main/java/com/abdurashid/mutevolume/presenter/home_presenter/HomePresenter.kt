package com.abdurashid.mutevolume.presenter.home_presenter

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.abdurashid.mutevolume.db.Today
import com.abdurashid.mutevolume.net.NetworkManager
import com.abdurashid.mutevolume.presenter.MainContract
import com.example.mutevolume.receiver.MyReceiver
import com.example.mutevolume.receiver.MyUnMuteReceiver
import com.google.gson.JsonObject
import io.reactivex.disposables.Disposable
import net.Service
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomePresenter(
    val context: Context,
    val view: MainContract.View,
    private val model: MainContract.Model
) :
    MainContract.Presenter {
    private val networkManager = NetworkManager(context)
    private val list = ArrayList<Today>()
    val cal = Calendar.getInstance()
    lateinit var disposable: Disposable

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

            
            list.add(Today(fajr, sunrise, dhuhr, asr, magrib, isha))

            view.addItem(list)
            // model.context(context)
            //  model.addToDB(list)
            Log.d(TAG, fajr)
        }
        //  view.addItem(list)
        Log.d("TodayList", "${list[1].fajr.substring(0, 2)}  ${list[1].fajr.substring(3, 5)}")
    }

    fun getAllTimes() {

        /* disposable = model.getInDB().doOnSuccess { view.showProgress(true) }
             .doAfterTerminate { view.showProgress(false) }
             .subscribe({
                 view.addItem(list)
             }, {
                 view.showMessage(it.message!!)
             })*/
    }

    fun alarm(position: Int) {

        val year = cal.get(Calendar.YEAR).toString()
        val month = cal.get(Calendar.MONTH).toString()
        val day = cal.get(Calendar.DAY_OF_MONTH).toString()

        val calendar = Calendar.getInstance()
        val today = list[position]
        Log.d("PositionPesenter", "$position")

        calendarFajr(year, month, day, calendar, today)
        calendarDhuhr(year, month, day, calendar, today)
        calendarAsr(year, month, day, calendar, today)
        calendarMaghrib(year, month, day, calendar, today)
        calendarIsha(year, month, day, calendar, today)
    }

    private fun calendarFajr(
        year: String,
        month: String,
        day: String,
        calendar: Calendar,
        today: Today
    ) {
        val hour = if (today.fajr.substring(0, 2).toInt() < 10) "0${
            today.fajr.substring(0, 2).toInt()
        }" else today.fajr.substring(0, 2)

        val minute = if (today.fajr.substring(3, 5).toInt() < 10) "0${
            today.fajr.substring(3, 5).toInt()
        }" else today.fajr.substring(3, 5)

        calendar.set(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour.toInt(),
            minute.toInt()
        )


        Log.d(TAG, "${today.fajr.substring(0, 2).toInt()} ${today.fajr.substring(3, 5).toInt()}")
        muteAlarm(calendar)
    }

    private fun calendarDhuhr(
        year: String,
        month: String,
        day: String,
        calendar: Calendar,
        today: Today
    ) {
        val hour = if (today.dhuhr.substring(0, 2).toInt() < 10) "0${
            today.dhuhr.substring(0, 2).toInt()
        }" else today.dhuhr.substring(0, 2)

        val minute = if (today.dhuhr.substring(3, 5).toInt() < 10) "0${
            today.dhuhr.substring(3, 5).toInt()
        }" else today.dhuhr.substring(3, 5)


        calendar.set(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour.toInt(),
            minute.toInt()
        )

        muteAlarm(calendar)
    }

    private fun calendarAsr(
        year: String,
        month: String,
        day: String,
        calendar: Calendar,
        today: Today
    ) {

        val hour = if (today.asr.substring(0, 2).toInt() < 10) "0${
            today.asr.substring(0, 2).toInt()
        }" else today.asr.substring(0, 2)

        val minute = if (today.asr.substring(3, 5).toInt() < 10) "0${
            today.asr.substring(3, 5).toInt()
        }" else today.asr.substring(3, 5)

        Log.d("AsrTime", "$hour $minute")


        calendar.set(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour.toInt(),
            minute.toInt()
        )

        muteAlarm(calendar)
    }

    private fun calendarMaghrib(
        year: String,
        month: String,
        day: String,
        calendar: Calendar,
        today: Today
    ) {

        val hour = if (today.maghrib.substring(0, 2).toInt() < 10) "0${
            today.maghrib.substring(0, 2).toInt()
        }" else today.maghrib.substring(0, 2)

        val minute = if (today.maghrib.substring(3, 5).toInt() < 10) "0${
            today.maghrib.substring(3, 5).toInt()
        }" else today.maghrib.substring(3, 5)


        calendar.set(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour.toInt(),
            minute.toInt()
        )

        muteAlarm(calendar)
    }

    private fun calendarIsha(
        year: String,
        month: String,
        day: String,
        calendar: Calendar,
        today: Today
    ) {

        val hour = if (today.isha.substring(0, 2).toInt() < 10) "0${
            today.isha.substring(0, 2).toInt()
        }" else today.isha.substring(0, 2)

        val minute = if (today.isha.substring(3, 5).toInt() < 10) "0${
            today.isha.substring(3, 5).toInt()
        }" else today.isha.substring(3, 5)

        calendar.set(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hour.toInt(),
            minute.toInt()
        )

        muteAlarm(calendar)
    }

    private fun muteAlarm(calendar: Calendar) {
        val intent = Intent(context, MyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        val currentTime = DateFormat.getDateTimeInstance().format(Date())
        Log.d("millisecond", "${calendar.timeInMillis} ${System.currentTimeMillis()}")
        Log.d("millisecond", "${calendar.time} $currentTime")

        if (calendar.time.equals(currentTime)) {
            view.alarm(1)
            unMuteAlarm(calendar)
        }
    }

    private fun unMuteAlarm(calendar: Calendar) {
        val intent = Intent(context, MyUnMuteReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + 3_00_000, pendingIntent)

        val currentTime = DateFormat.getDateTimeInstance().format(Date())
        Log.d("millisecond", "${calendar.timeInMillis} ${System.currentTimeMillis()}")
        Log.d("millisecond", "${calendar.time} $currentTime")
        if (calendar.time.equals(currentTime)) {
            view.alarm(2)
        }
    }

    fun addTime(hours: Int, minute: Int) {
        val year = cal.get(Calendar.YEAR).toString()
        val month = cal.get(Calendar.MONTH).toString()
        val day = cal.get(Calendar.DAY_OF_MONTH).toString()

        val calendar = Calendar.getInstance()

        calendar.set(
            year.toInt(),
            month.toInt(),
            day.toInt(),
            hours,
            minute
        )

        muteAlarm(calendar)

    }

    fun onBackPressed() {
        // disposable.dispose()
    }

    companion object {
        private const val TAG = "HomePresenter"
    }
}