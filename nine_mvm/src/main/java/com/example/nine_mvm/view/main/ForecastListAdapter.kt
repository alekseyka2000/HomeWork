package com.example.nine_mvm.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nine_mvm.R
import com.example.nine_mvm.model.entity.ConvertedForecastData

class ForecastListAdapter(
    private val context: Context
) : RecyclerView.Adapter<ForecastListAdapter.ForecastHolder>() {

    var forecastList: List<ConvertedForecastData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ForecastHolder(LayoutInflater.from(context), parent)

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        holder.bind(forecastList[position])
    }

    override fun getItemCount() = forecastList.size

    fun updateList(oldList: List<ConvertedForecastData>, newList: List<ConvertedForecastData>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].data == newList[newItemPosition].data
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    inner class ForecastHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.forecast_item, parent, false)) {

        private val time = itemView.findViewById<TextView>(R.id.timeTextView)
        private val description = itemView.findViewById<TextView>(R.id.forecastDescriptionTextView)
        private val temperature = itemView.findViewById<TextView>(R.id.tempTextView)

        fun bind(forecast: ConvertedForecastData) {
            time?.text = forecast.data
            description?.text = forecast.description
            temperature?.text = forecast.temperature
        }
    }
}