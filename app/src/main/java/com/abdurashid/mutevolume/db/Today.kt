package com.abdurashid.mutevolume.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Today(
    val fajr: String = "",
    val sunrise: String = "",
    val dhuhr: String = "",
    val asr: String = "",
    val maghrib: String = "",
    val isha: String = "",
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
) {

   /* fun toPattern(): NamazTimes {
        //Fajr
        val fajrH = fajr.substring(0, 2)
        if (fajrH.toInt() < 10) "0${(fajrH.toInt())}" else (fajrH.toInt()).toString()
        val fajrM = fajr.substring(3, 5)
        if (fajrM.toInt() < 10) "0${(fajrM.toInt())}" else (fajrM.toInt()).toString()

        // Sunrise
        val sunriseH = sunrise.substring(0, 2)
        if (sunriseH.toInt() < 10) "0${(sunriseH.toInt())}" else (sunriseH.toInt()).toString()
        val sunriseM = sunrise.substring(3, 5)
        if (sunriseM.toInt() < 10) "0${(sunriseM.toInt())}" else (sunriseM.toInt()).toString()

        // Dhuhr
        val dhuhrH = dhuhr.substring(0, 2)
        if (dhuhrH.toInt() < 10) "0${(dhuhrH.toInt())}" else (dhuhrH.toInt()).toString()
        val dhuhrM = dhuhr.substring(3, 5)
        if (dhuhrM.toInt() < 10) "0${(dhuhrM.toInt())}" else (dhuhrM.toInt()).toString()

        // Asr
        val asrH = asr.substring(0, 2)
        if (asrH.toInt() < 10) "0${(asrH.toInt())}" else (asrH.toInt()).toString()
        val asrM = asrH.substring(3, 5)
        if (asrM.toInt() < 10) "0${(asrM.toInt())}" else (asrM.toInt()).toString()

        // Maghrib
        val maghribH = maghrib.substring(0, 2)
        if (maghribH.toInt() < 10) "0${(maghribH.toInt())}" else (maghribH.toInt()).toString()
        val maghribM = maghrib.substring(3, 5)
        if (maghribM.toInt() < 10) "0${(maghribM.toInt())}" else (maghribM.toInt()).toString()

        // Isha
        val ishaH = isha.substring(0, 2)
        if (ishaH.toInt() < 10) "0${(ishaH.toInt())}" else (ishaH.toInt()).toString()
        val ishaM = isha.substring(3, 5)
        if (ishaM.toInt() < 10) "0${(ishaM.toInt())}" else (ishaM.toInt()).toString()

        return NamazTimes(
            fajrH,
            fajrM,
            sunriseH,
            sunriseM,
            dhuhrH,
            dhuhrM,
            asrH,
            asrM,
            maghribH,
            maghribM,
            ishaH,
            ishaM
        )
    }

    override fun toString(): String {
        return ""
    }*/
}

data class NamazTimes(
    val fajrH: String,
    val fajrM: String,
    val sunriseH: String,
    val sunriseM: String,
    val dhuhrH: String,
    val dhuhrM: String,
    val asrH: String,
    val asrM: String,
    val maghribH: String,
    val maghribM: String,
    val ishaH: String,
    val ishaM: String
)
