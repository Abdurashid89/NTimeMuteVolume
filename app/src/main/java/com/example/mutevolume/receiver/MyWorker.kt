package com.example.mutevolume.receiver

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

const val FAJR_HOURSE = "fajr_hourse"
const val FAJR_MINUTE = "fajr_minute"

const val DHUHR_HOURSE = "dhuhr_hourse"
const val DHUHR_MINUTE = "dhuhr_minute"

const val ASR_HOURSE = "asr_hourse"
const val ASR_MINUTE = "asr_minute"

const val MAGHRIB_HOURSE = "maghrib_hourse"
const val MAGHRIB_MINUTE = "maghrib_minute"

const val ISHA_HOURSE = "isha_hourse"
const val ISHA_MINUTE = "isha_minute"

const val RESULT_KEY_FAJR_H = "result_key_fajr_hourse"
const val RESULT_KEY_FAJR_M = "result_key_fajr_minute"

const val RESULT_KEY_DHUHR_H = "result_key_dhuhr_hourse"
const val RESULT_KEY_DHUHR_M = "result_key_dhuhr_minute"

const val RESULT_KEY_ASR_H = "result_key_asr_hourse"
const val RESULT_KEY_ASR_M = "result_key_asr_minute"

const val RESULT_KEY_MAGHRIB_H = "result_key_maghrib_hourse"
const val RESULT_KEY_MAGHRIB_M = "result_key_maghrib_minute"

const val RESULT_KEY_ISHA_H = "result_key_isha_hourse"
const val RESULT_KEY_ISHA_M = "result_key_isha_minute"

class MyWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(
    context, workerParams
) {

    override fun doWork(): Result {
        val fajrHourse = inputData.getString(FAJR_HOURSE)
        val fajrMinute = inputData.getString(FAJR_MINUTE)
/*        val fajrMinute = inputData.getInt(FAJR_MINUTE, 0)

        val dhuhrHourse = inputData.getInt(DHUHR_HOURSE, 0)
        val dhuhrMinute = inputData.getInt(DHUHR_MINUTE, 0)

        val asrHourse = inputData.getInt(ASR_HOURSE, 0)
        val asrMinute = inputData.getInt(ASR_MINUTE, 0)

        val maghribHourse = inputData.getInt(MAGHRIB_HOURSE, 0)
        val maghribMinute = inputData.getInt(MAGHRIB_MINUTE, 0)

        val ishaHourse = inputData.getInt(ISHA_HOURSE, 0)
        val ishaMinute = inputData.getInt(ISHA_MINUTE, 0)*/


        val output: Data = workDataOf(
            RESULT_KEY_FAJR_H to fajrHourse,
            RESULT_KEY_FAJR_M to fajrMinute
            /*,
            RESULT_KEY_FAJR_M to fajrMinute,
            RESULT_KEY_DHUHR_H to dhuhrHourse,
            RESULT_KEY_DHUHR_M to dhuhrMinute,
            RESULT_KEY_ASR_H to asrHourse,
            RESULT_KEY_ASR_M to asrMinute,
            RESULT_KEY_MAGHRIB_H to maghribHourse,
            RESULT_KEY_MAGHRIB_M to maghribMinute,
            RESULT_KEY_ISHA_H to ishaHourse,
            RESULT_KEY_ISHA_M to ishaMinute*/
        )

        return Result.success(output)
    }

    override fun onStopped() {
        super.onStopped()
        Log.d("MyWorker", "stoped worker")
    }

}