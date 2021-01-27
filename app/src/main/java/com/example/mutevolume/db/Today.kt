package com.example.mutevolume.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Today(
    @PrimaryKey(autoGenerate = false)
    val fajr: String = "",
    val sunrise: String = "",
    val dhuhr: String = "",
    val asr: String = "",
    val maghrib: String = "",
    val isha: String = ""
)
