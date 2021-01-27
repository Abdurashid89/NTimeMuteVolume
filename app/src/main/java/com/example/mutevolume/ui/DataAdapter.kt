package com.example.mutevolume.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mutevolume.R
import com.example.mutevolume.db.Today
import java.util.*
import kotlin.collections.ArrayList

class DataAdapter(private val list: ArrayList<Today>) : RecyclerView.Adapter<DataHolder>() {
    val calendar = Calendar.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_item, parent, false)
        return DataHolder(root!!)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = calendar.get(Calendar.DAY_OF_MONTH - 1)


}

class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvMonthName: TextView = itemView.findViewById(R.id.tvMonthName)
    var tvWeekName: TextView = itemView.findViewById(R.id.tvWeekName)
    var tvHjriyName: TextView = itemView.findViewById(R.id.tvHijriy)
    var tvBomdod: TextView = itemView.findViewById(R.id.bomdodTime)
    var tvQuyosh: TextView = itemView.findViewById(R.id.quyoshTime)
    var tvPeshin: TextView = itemView.findViewById(R.id.peshinTime)
    var tvAsr: TextView = itemView.findViewById(R.id.asrTime)
    var tvShom: TextView = itemView.findViewById(R.id.shomTime)
    var tvXufton: TextView = itemView.findViewById(R.id.huftonTime)

    fun onBind(data: Today) {

        tvMonthName.text
        tvWeekName.text
        tvHjriyName.text
        tvBomdod.text = data.fajr
        tvQuyosh.text = data.sunrise
        tvPeshin.text = data.dhuhr
        tvAsr.text = data.asr
        tvShom.text = data.maghrib
        tvXufton.text = data.isha

    }

}

/**
 *   val fajr: String? = null,
val sunrise: String? = null,
val dhuhr: String? = null,
val asr: String? = null,
val sunset: String? = null,
val maghrib: String? = null,
val isha: String? = null,
val imsak: String? = null,
val midnight: String? = null*/