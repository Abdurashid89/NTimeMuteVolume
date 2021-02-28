package com.abdurashid.mutevolume.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdurashid.mutevolume.R
import com.abdurashid.mutevolume.db.Today
import com.abdurashid.mutevolume.util.SingleBlock
import java.util.*

class DataAdapter : RecyclerView.Adapter<DataHolder>() {
    private val calendar = Calendar.getInstance()
    private val list = ArrayList<Today>()

    var listener: SingleBlock<Today>? = null

    var positionListener: SingleBlock<Int>? = null

    fun setOnClickListener(block: SingleBlock<Today>) {
        listener = block
    }

    fun adapterPosition(position: SingleBlock<Int>) {
        positionListener = position
    }

    fun submitList(dayList: ArrayList<Today>) {
        list.clear()
        list.addAll(dayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_item, parent, false)
        return DataHolder(root!!)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val day = list[calendar[Calendar.DAY_OF_MONTH] - 1]

//        listener!!.invoke(day)
        positionListener?.invoke(calendar[Calendar.DAY_OF_MONTH] - 1)
        holder.onBind(day, position)

    }

    override fun getItemCount() = 1

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

    fun onBind(data: Today, position: Int) {

        tvAsr.setOnClickListener { data }

        tvMonthName.text
        tvWeekName.text = "$position"
        tvHjriyName.text
        tvBomdod.text = data.fajr
        tvQuyosh.text = data.sunrise
        tvPeshin.text = data.dhuhr
        tvAsr.text = data.asr
        tvShom.text = data.maghrib
        tvXufton.text = data.isha

    }

}