package com.abdurashid.mutevolume.util

data class Times(
    val year: String,
    val day: String,
    val month: String,
    val hour: String,
    val minute: String
) {

    fun toPattern(): Times {
        val m =
            if (month.toInt() < 10) "0${(month.toInt() + 1)}" else (month.toInt() + 1).toString()
        val d = if (day.toInt() < 10) "0$day" else day
        val h = if (hour.toInt() < 10) "0$hour" else hour
        val min = if (minute.toInt() < 10) "0$minute" else minute
        return Times(year, m, d, h,min)
    }

    override fun toString(): String {
        return "$year.$month.$day.$hour:$minute:00"
    }
}