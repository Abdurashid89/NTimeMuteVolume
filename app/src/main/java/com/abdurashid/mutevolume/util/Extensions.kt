@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.abdurashid.mutevolume.util

import android.annotation.SuppressLint
import android.view.View
import java.text.SimpleDateFormat
import java.util.*


fun View.visible(visisble: Boolean) {
    this.visibility = if (visisble) View.VISIBLE else View.GONE
}


fun Date.toString(format: String, locale: Locale = Locale.US): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun formatDate(): String {
    return getCurrentDateTime().toString("yyyy-MM-dd.hh.mm")
}


@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd.HH:mm")
    return format.format(date)
}

fun currentTimeToLong(): Long {
    return System.currentTimeMillis()
}

@SuppressLint("SimpleDateFormat")
fun convertDateToLong(date: String): Long {
    val df = SimpleDateFormat("yyyy.MM.dd.HH:mm:ss")
    return df.parse(date).time
}

typealias SingleBlock <T> = (T) -> Unit
