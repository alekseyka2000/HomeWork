package com.example.nine_mvp.view.city_chooser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nine_mvp.R

class CityListAdapter(
    private val context: Context,
    private val selectedCity: String?,
    private val cellClickListener: (String) -> Unit
) : RecyclerView.Adapter<CityListAdapter.CityHolder>() {

    var cityList: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityHolder(LayoutInflater.from(context), parent)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.apply {
            bind(cityList[position])
            itemView.setOnClickListener { cellClickListener(cityList[position]) }
        }
    }

    override fun getItemCount() = cityList.size

    fun updateList(oldList: List<String>, newList: List<String>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
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

    inner class CityHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.city_item, parent, false)) {

        private val cityTextView = itemView.findViewById<TextView>(R.id.cityNameText)
        private val checkedCity = itemView.findViewById<ImageView>(R.id.checkedCity)

        fun bind(name: String) {
            cityTextView?.text = name
            if(name != selectedCity) checkedCity.visibility = View.INVISIBLE
        }
    }
}