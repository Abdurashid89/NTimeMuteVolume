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
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Please Wait...")

        if (show) progressDialog.show() else progressDialog.cancel()
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}