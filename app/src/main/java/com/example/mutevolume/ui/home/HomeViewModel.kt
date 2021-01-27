package com.example.mutevolume.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.impl.Schedulers
import com.google.gson.JsonObject
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import net.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeViewModel : ViewModel() {
    private var _message = MutableLiveData<String>()
    private var _data = MutableLiveData<JsonObject>()


    @SuppressLint("CheckResult")
    fun getResponse(service: Service): LiveData<JsonObject> {
        val calendar: Calendar = Calendar.getInstance()
        service.getMonthById(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), 41.1, 69.11)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                 _data.value =   it
            }, {

            })



        val data: LiveData<JsonObject> = _data
        return data
    }

    val message : LiveData<String> = _message


}
