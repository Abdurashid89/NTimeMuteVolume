package com.abdurashid.mutevolume.ui

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abdurashid.mutevolume.R
import com.abdurashid.mutevolume.db.Today
import com.abdurashid.mutevolume.net.visible
import com.abdurashid.mutevolume.presenter.MainContract
import com.abdurashid.mutevolume.presenter.home_presenter.HomePresenter
import com.abdurashid.mutevolume.presenter.home_presenter.HomeRepository
import com.abdurashid.mutevolume.ui.adapter.DataAdapter
import com.google.android.material.snackbar.Snackbar
import com.labo.kaji.fragmentanimations.CubeAnimation
import net.NetWork
import java.util.*


@Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS", "NAME_SHADOWING")
class HomeFragment : BaseFragment(), MainContract.View {
    override val resId = R.layout.fragment_home

    lateinit var progress: ProgressBar
    var rvData: RecyclerView? = null
    lateinit var adapter: DataAdapter

    lateinit var service: net.Service
    lateinit var presenter: HomePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = NetWork().service
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DataAdapter()
        rvData = view.findViewById(R.id.rvData)
        progress = view.findViewById(R.id.progress)

        presenter = HomePresenter(requireContext(), this, HomeRepository())
        presenter.netWork(service)

        presenter.getAllTimes()
        view.findViewById<Button>(R.id.btnEnter).setOnClickListener {

            val hourse = view.findViewById<EditText>(R.id.etHours)
            val minute = view.findViewById<EditText>(R.id.etMinute)

            if (hourse.text.toString().isNotEmpty() && minute.text.toString().isNotEmpty()) {
                presenter.addTime(hourse.text.toString().toInt(), minute.text.toString().toInt())
                Snackbar.make(
                    requireView(),
                    "Alarm Saved to time ${hourse.text}:${minute.text}",
                    Snackbar.LENGTH_SHORT
                ).show()
                hourse.setText("")
                minute.setText("")

            } else {
                Snackbar.make(requireView(), "Please choose time", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return CubeAnimation.create(CubeAnimation.UP, enter, 1000)
    }

    override fun addItem(list: ArrayList<Today>) {
        adapter = DataAdapter()
        adapter.submitList(list)

        rvData!!.adapter = adapter
        adapter.adapterPosition { presenter.alarm(it) }
    }

    override fun showProgress(isShow: Boolean) {
        progress.visible(isShow)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun alarm(type: Int) {
        if (type == 1) {
            requestMutePermission()
        } else {
            requestUnMutePermission()
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Alarm Dialog")
            dialog.setMessage("RINGER_MODE_NORMAL on")
            dialog.setPositiveButton(
                "OK"
            ) { dialog, _ ->
                dialog.dismiss()
            }
            dialog.show()
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
                    /*  audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                      kotlin.run { Thread.sleep(5000) }*/
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

    private fun requestUnMutePermission() {
        try {
            if (android.os.Build.VERSION.SDK_INT < 23) {
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            } else if (android.os.Build.VERSION.SDK_INT >= 23) {
                val notificationManager: NotificationManager =
                    requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


                if (notificationManager.isNotificationPolicyAccessGranted) {
                    val audioManager: AudioManager =
                        requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    /*  audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                      kotlin.run { Thread.sleep(5000) }*/
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.onBackPressed()
    }

}