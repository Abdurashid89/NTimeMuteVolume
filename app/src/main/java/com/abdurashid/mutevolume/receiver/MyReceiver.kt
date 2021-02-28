package com.example.mutevolume.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager

class MyReceiver : BroadcastReceiver() {

val audioManager : AudioManager? = null

    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        if (android.os.Build.VERSION.SDK_INT < 23) {
            audioManager!!.ringerMode = AudioManager.RINGER_MODE_SILENT
        } else if (android.os.Build.VERSION.SDK_INT >= 23) {
            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            if (notificationManager.isNotificationPolicyAccessGranted) {
                val audioManager: AudioManager =
                    context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
             /*   audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                kotlin.run { Thread.sleep(5000) }*/
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT

            }

           /* if (action.equals("ALARM")){
             unMute(context)
            }*/
        }
    }

    private fun unMute(context: Context) {
        if (android.os.Build.VERSION.SDK_INT < 23) {
            audioManager!!.ringerMode = AudioManager.RINGER_MODE_SILENT
        } else if (android.os.Build.VERSION.SDK_INT >= 23) {
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            if (notificationManager.isNotificationPolicyAccessGranted) {
                val audioManager: AudioManager =
                    context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//                audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
//                kotlin.run { Thread.sleep(5000) }
                audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL

            }
        }
    }

    }
