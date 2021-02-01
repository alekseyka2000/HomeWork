package com.example.ten

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class SongListAdapter(private val cellClickListener: (String) -> Unit) :
    RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var selectedItem: Int? = null

    var songList: List<String> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        notifyChanges(oldValue, newValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SongViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.apply {
            bind(songList[position], position, selectedItem)
            itemView.setOnClickListener {
                cellClickListener(songList[position])
                selectedItem = if (selectedItem == null) {
                    position
                } else {
                    if (selectedItem == position) null
                    else {
                        notifyItemChanged(selectedItem!!)
                        position
                    }
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = songList.size

    private fun notifyChanges(
        oldList: List<String>,
        newList: List<String>
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    class SongViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) :
        RecyclerView.ViewHolder(
            inflater
                .inflate(R.layout.song_item, parent, false)
        ) {
        private var playImageView: ImageView = itemView.findViewById(R.id.playImage)
        private var songNameTextView: TextView = itemView.findViewById(R.id.songName)

        fun bind(song: String, position: Int, selected: Int?) {
            songNameTextView.text = song
            playImageView.isVisible = position == selected
        }
    }
}