package com.example.mutevolume.ui

import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

@Suppress("DEPRECATION")
abstract class BaseFragment : Fragment() {
    abstract val resId: Int
    lateinit var audioManager: AudioManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(container?.context)
            .inflate(resId, container, false)
    }


    fun showDialog(show: Boolean) {
        var progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Please Wait...")

        if (show) progressDialog.show() else progressDialog.dismiss()
    }

    fun showToast(message: LiveData<String>) {
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
    }


    /*fun requestMutePermission() {
        try {
            if (android.os.Build.VERSION.SDK_INT < 23) {
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            } else if (android.os.Build.VERSION.SDK_INT >= 23) {
                val notificationManager: NotificationManager =
                    requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                if (notificationManager.isNotificationPolicyAccessGranted) {
                    val audioManager: AudioManager =
                        requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                    kotlin.run { Thread.sleep(5000) }
                    audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT

                } else {
                    val intent =
                        Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    startActivityForResult(intent, 1001)
                }

            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            requestMutePermission()
        }
    }*/

}