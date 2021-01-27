package com.example.mutevolume.net

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission

class NetworkManager constructor(private val context: Context) {

        @RequiresPermission(ACCESS_NETWORK_STATE)
        fun isConnected(): Boolean {
            val info: NetworkInfo? = getActiveNetworkInfo()
            return info != null && info.isConnected
        }

        @SuppressLint("ServiceCast")
        @RequiresPermission(ACCESS_NETWORK_STATE)
        private fun getActiveNetworkInfo(): NetworkInfo? {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo
        }
    }
