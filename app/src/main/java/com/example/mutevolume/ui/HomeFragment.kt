package com.example.mutevolume.ui

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.mutevolume.R
import com.example.mutevolume.db.Today
import com.example.mutevolume.net.visible
import com.example.mutevolume.presenter.MainContract
import com.example.mutevolume.presenter.home_presenter.HomePresenter
import com.example.mutevolume.presenter.home_presenter.HomeRepository
import com.example.mutevolume.receiver.FAJR_MINUTE
import com.example.mutevolume.receiver.MyWorker
import com.example.mutevolume.receiver.RESULT_KEY_FAJR_M
import com.example.mutevolume.ui.adapter.DataAdapter
import com.labo.kaji.fragmentanimations.CubeAnimation
import net.NetWork
import java.util.*


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
    lateinit var day: Today
    lateinit var progress: ProgressBar

    override val resId = R.layout.fragment_home
    lateinit var service: net.Service

    var rvData: RecyclerView? = null
    lateinit var adapter: DataAdapter
    lateinit var presenter: HomePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = NetWork().service
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvData = view.findViewById(R.id.rvData)
        progress = view.findViewById(R.id.progress)

        presenter = HomePresenter(requireContext(), this, HomeRepository())
        presenter.netWork(service)
        alarm()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return CubeAnimation.create(CubeAnimation.UP, enter, 1000)
    }

    override fun addItem(list: ArrayList<Today>) {
        adapter = DataAdapter(list)

        day = list[22]
        rvData!!.adapter = adapter

        fajrHourse = day.fajr.substring(0, 2).toInt()
        fajrMinute = day.fajr.substring(3, 5).toInt()

        sunriseHourse = day.sunrise.substring(0, 2).toInt()
        sunriseMinute = day.sunrise.substring(3, 5).toInt()

        dhuhrHourse = day.dhuhr.substring(0, 2).toInt()
        dhuhrMinute = day.dhuhr.substring(3, 5).toInt()

        asrHourse = day.dhuhr.substring(0, 2).toInt()
        asrMinute = day.dhuhr.substring(3, 5).toInt()

        maghribHourse = day.maghrib.substring(0, 2).toInt()
        maghribMinute = day.maghrib.substring(3, 5).toInt()

        ishaHourse = day.isha.substring(0, 2).toInt()
        ishaMinute = day.isha.substring(3, 5).toInt()

    }

    override fun showProgress(isShow: Boolean) {
        progress.visible(isShow)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun alarm() {

        /*val myData: Data = workDataOf(
            FAJR_HOURSE to fajrHourse,
            FAJR_MINUTE to fajrMinute,
            DHUHR_HOURSE to dhuhrHourse,
            DHUHR_MINUTE to dhuhrMinute,
            ASR_HOURSE to asrHourse,
            ASR_MINUTE to asrMinute,
            MAGHRIB_HOURSE to maghribHourse,
            MAGHRIB_MINUTE to maghribMinute,
            ISHA_HOURSE to ishaHourse,
            ISHA_MINUTE to ishaMinute
        )*/
//        val realTime = "${cal.get(Calendar.HOUR_OF_DAY)}${cal.get(Calendar.MINUTE)}"
        val realTime = Date().time

        val time = "1:29"
        val t = time.replace(":", "")
        val myData = workDataOf(FAJR_MINUTE to t)
        val myWork = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(myData)
            .build()

        WorkManager.getInstance(requireContext())
            .enqueue(myWork)

        WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(myWork.id)
            .observe(viewLifecycleOwner, { info ->
                if (info != null && info.state.isFinished) {

//                    val fHourse = info.outputData.getString(RESULT_KEY_FAJR_H)
                    val fMinute = info.outputData.getString(RESULT_KEY_FAJR_M)

                    val h = fMinute!!.substring(0, 1)
                    val m = fMinute.substring(1, 3)
                    val t = (h.toLong() * 60) + m.toLong()

                    Log.d("TIME", "${realTime} == $fMinute $t")
                    if (realTime == t) {
                        requestMutePermission()
                    }
/*                    val fMinute = info.outputData.getInt(RESULT_KEY_FAJR_M, 0)


                    val dHourse = info.outputData.getInt(RESULT_KEY_DHUHR_H, 0)
                    val dMinute = info.outputData.getInt(RESULT_KEY_DHUHR_M, 0)

                    val aHourse = info.outputData.getInt(RESULT_KEY_ASR_H, 0)
                    val aMinute = info.outputData.getInt(RESULT_KEY_ASR_M, 0)

                    val mHourse = info.outputData.getInt(RESULT_KEY_MAGHRIB_H, 0)
                    val mMinute = info.outputData.getInt(RESULT_KEY_MAGHRIB_M, 0)

                    val iHourse = info.outputData.getInt(RESULT_KEY_ISHA_H, 0)
                    val iMinute = info.outputData.getInt(RESULT_KEY_ISHA_M, 0)*/

/*                    when {
                        fHourse == cal.get(Calendar.HOUR_OF_DAY) && fMinute == cal.get(Calendar.MINUTE) -> requestMutePermission()
                        dHourse == cal.get(Calendar.HOUR_OF_DAY) && dMinute == cal.get(Calendar.MINUTE) -> requestMutePermission()
                        aHourse == cal.get(Calendar.HOUR_OF_DAY) && aMinute == cal.get(Calendar.MINUTE) -> requestMutePermission()
                        mHourse == cal.get(Calendar.HOUR_OF_DAY) && mMinute == cal.get(Calendar.MINUTE) -> requestMutePermission()
                        iHourse == cal.get(Calendar.HOUR_OF_DAY) && iMinute == cal.get(Calendar.MINUTE) -> requestMutePermission()
                    }*/
                }
            })
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