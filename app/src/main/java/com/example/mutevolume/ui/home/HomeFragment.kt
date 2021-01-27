package com.example.mutevolume.ui.home

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mutevolume.R
import com.example.mutevolume.db.Today
import com.example.mutevolume.presenter.MainContract
import com.example.mutevolume.presenter.home_presenter.HomePresenter
import com.example.mutevolume.presenter.home_presenter.HomeRepository
import com.example.mutevolume.receiver.MyReceiver
import com.example.mutevolume.ui.BaseFragment
import com.example.mutevolume.ui.DataAdapter
import net.NetWork
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

@Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS")
class HomeFragment : BaseFragment(), MainContract.View {
    var fajrHourse = -1
    var fajrMinute = -1
    var sunriseHourse = -1
    var sunriseMinute = -1
    var dhuhrHourse = -1
    var dhuhrMinute = -1
    var asrHourse = -1
    var asrMinute = -1
    var maghribHourse = -1
    var maghribMinute = -1
    var ishaHourse = -1
    var ishaMinute = -1
    val cal = Calendar.getInstance()

    override val resId = R.layout.fragment_home
    lateinit var service: net.Service
    private lateinit var homeViewModel: HomeViewModel
    var rvData: RecyclerView? = null
    lateinit var adapter: DataAdapter
    lateinit var presenter: HomePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        service = NetWork().service

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvData = view.findViewById(R.id.rvData)
        presenter = HomePresenter(requireContext(), this, HomeRepository())
        homeViewModel.getResponse(service).observe(viewLifecycleOwner, { presenter.addData(it) })
        alarm()
    }


    override fun addItem(list: ArrayList<Today>) {

        val today = list[cal.get(Calendar.DAY_OF_MONTH - 1)]
        fajrHourse = today.fajr.substring(0, 2).toInt()
        fajrMinute = today.fajr.substring(3, 5).toInt()

        sunriseHourse = today.sunrise.substring(0, 2).toInt()
        sunriseMinute = today.sunrise.substring(3, 5).toInt()

        dhuhrHourse = today.dhuhr.substring(0, 2).toInt()
        dhuhrMinute = today.dhuhr.substring(3, 5).toInt()

        asrHourse = today.dhuhr.substring(0, 2).toInt()
        asrMinute = today.dhuhr.substring(3, 5).toInt()

        maghribHourse = today.maghrib.substring(0, 2).toInt()
        maghribMinute = today.maghrib.substring(3, 5).toInt()

        ishaHourse = today.isha.substring(0, 2).toInt()
        ishaMinute = today.isha.substring(3, 5).toInt()

        Log.d("CCC", "$fajrHourse  $fajrMinute")
        Log.d(
            "BBB",
            "${today.fajr} ${today.sunrise} ${today.dhuhr} ${today.asr} ${today.maghrib} ${today.isha}"
        )

        adapter = DataAdapter(list)
        rvData!!.adapter = adapter
    }

    private fun alarm() {

        val intent = Intent(requireContext(), MyReceiver::class.java)
        intent.action = "myAction"
        intent.putExtra("name", "myValue")
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 2001, intent, 0)
        val alarmManager: AlarmManager =
            requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
//        val currentDateTime = DateFormat.getDateTimeInstance().format(Date())
        if (cal.get(Calendar.HOUR_OF_DAY) == fajrHourse.toInt() && cal.get(
                Calendar.MINUTE
            ) == fajrMinute.toInt()
            || cal.get(Calendar.HOUR_OF_DAY) == dhuhrHourse && cal.get(Calendar.MINUTE) == dhuhrMinute
            || cal.get(Calendar.HOUR_OF_DAY) == asrHourse && cal.get(Calendar.MINUTE) == asrMinute ||
            cal.get(Calendar.HOUR_OF_DAY) == maghribHourse && cal.get(Calendar.MINUTE) == maghribMinute
            || cal.get(Calendar.HOUR_OF_DAY) == ishaHourse && cal.get(Calendar.MINUTE) == ishaMinute
        ) {
            requestMutePermission()
        }
    }

    private fun requestMutePermission() {
        try {
            if (android.os.Build.VERSION.SDK_INT < 23) {
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            } else if (android.os.Build.VERSION.SDK_INT >= 23) {
                val notificationManager: NotificationManager =
                    requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                if (notificationManager.isNotificationPolicyAccessGranted) {
                    val audioManager: AudioManager =
                        requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
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
    }

}