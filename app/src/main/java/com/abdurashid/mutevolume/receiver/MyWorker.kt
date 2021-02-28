package com.abdurashid.mutevolume.receiver

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

const val FAJR = "fajr_hourse"

const val RESULT_KEY_FAJR = "result_key_fajr_hourse"

class MyWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(
    context, workerParams
) {

    override fun doWork(): Result {
        val fajrHourse = inputData.getLong(FAJR,0)

//    val realTime =  System.currentTimeMillis() - fajrHourse

//    Thread.sleep(realTime)
    val output: Data = workDataOf(RESULT_KEY_FAJR to true)
    Log.d("MyWorker", "start worker $fajrHourse ${System.currentTimeMillis()}")
    return Result.success(output)
    }

    override fun onStopped() {
        super.onStopped()
        Log.d("MyWorker", "stoped worker")
    }

}