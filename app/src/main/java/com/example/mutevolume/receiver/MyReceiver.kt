package com.example.mutevolume.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.audiofx.BassBoost
import android.provider.Settings
import android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
import androidx.core.app.ActivityCompat.startActivityForResult

class MyReceiver : BroadcastReceiver() {
    lateinit var audioManager: AudioManager

    override fun onReceive(context: Context?, intent: Intent?) {

    }
}