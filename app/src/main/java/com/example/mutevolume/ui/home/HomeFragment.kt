package com.example.mutevolume.ui.home

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Bundle
import android.provider.CalendarContract
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
    var fajr by Delegates.notNull<Int>()
    var sunrise by Delegates.notNull<Int>()
    var dhuhr by Delegates.notNull<Int>()
    var asr by Delegates.notNull<Int>()
    var maghrib by Delegates.notNull<Int>()
    var isha by Delegates.notNull<Int>()

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
        alarm()
        presenter = HomePresenter(requireContext(), this, HomeRepository())
        homeViewModel.getResponse(service).observe(viewLifecycleOwner, { presenter.addData(it) })
    }


    override fun addItem(list: ArrayList<Today>) {

        list.forEach {
            fajr = it.fajr.toInt()
            sunrise = it.sunrise.toInt()
            dhuhr = it.dhuhr.toInt()
            maghrib = it.maghrib.toInt()
            isha = it.isha.toInt()
        }

        adapter = DataAdapter(list)
        rvData!!.adapter = adapter
    }

    private fun alarm() {
        val cal = Calendar.getInstance()

        val intent = Intent(requireContext(), MyReceiver::class.java)
        intent.action = "myAction"
        intent.putExtra("name", "myValue")
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 2001, intent, 0)
        val alarmManager: AlarmManager =
            requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
        val currentDateTime = DateFormat.getDateTimeInstance().format(Date())
        if (cal.get(Calendar.HOUR_OF_DAY) == fajr && cal.get(Calendar.HOUR_OF_DAY) == 12 && cal.get(
                Calendar.MINUTE
            ) == 46
            || cal.get(Calendar.MILLISECOND) == 0
        ) {
            requestMutePermission()
            Log.d("AAA", "${cal.get(Calendar.HOUR_OF_DAY)}")
            Log.d("AAA", "${cal.get(Calendar.HOUR)}")
            Log.d("AAA", "${cal.get(Calendar.MINUTE)}")
        }
    }

    fun requestMutePermission() {
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