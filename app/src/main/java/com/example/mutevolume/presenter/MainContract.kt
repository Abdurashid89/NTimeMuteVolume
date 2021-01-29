package com.example.mutevolume.presenter

import android.content.Context
import com.example.mutevolume.db.Today
import com.google.gson.JsonObject
import io.reactivex.Single

interface MainContract {

    interface Model {

        fun context(context: Context)

        fun addToDB(list: ArrayList<Today>)

        fun getInDB(): Single<ArrayList<Today>>

    }

    interface View {

        fun addItem(list: ArrayList<Today>)


    }

    interface Presenter {

        fun addData(data: JsonObject)

    }

}