package com.abdurashid.mutevolume.presenter

import android.content.Context
import com.abdurashid.mutevolume.db.Today
import com.google.gson.JsonObject
import io.reactivex.Single
import net.Service
import java.util.*

interface MainContract {

    interface Model {

        fun context(context: Context)

        fun addToDB(list: ArrayList<Today>)

        fun getInDB(): Single<ArrayList<Today>>
        fun getTimes(service: Service): Single<JsonObject>

    }

    interface View {

        fun addItem(list: ArrayList<Today>)
        fun showProgress(isShow: Boolean)
        fun showMessage(message: String)
        fun alarm(type: Int)
    }

    interface Presenter

}