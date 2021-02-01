package com.example.ten

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var serviceActions: ServiceActions? = null
    private lateinit var songListAdapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songListAdapter = SongListAdapter { selectSong: String -> itemClick(selectSong) }

        songListAdapter.songList = R.raw::class.java.fields.map { it.name }

        findViewById<RecyclerView>(R.id.songRecycler).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = songListAdapter
        }
    }

    private fun itemClick(selectSong: String) {
        if (serviceActions == null)
            startService(MediaService.getIntent(this@MainActivity, selectSong))
        else {
            stopService(MediaService.getIntent(this))
            if (serviceActions?.getCurrentSong() != selectSong && serviceActions?.getCurrentSong() != null)
                startService(MediaService.getIntent(this, selectSong))
        }
        bindService(
            Intent(this@MainActivity, MediaService::class.java),
            serviceConnection,
            0
        )
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceActions = (service as MediaService.ServiceBinder).getService()
            (serviceActions as MediaService).setListener{ currentSong: Int -> trackChangListener(currentSong)}
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceActions = null
        }
    }

    private fun trackChangListener(song: Int){
        songListAdapter.apply {
            val privSong = selectedItem
            selectedItem = song
            notifyItemChanged(song)
            privSong?.let { notifyItemChanged(privSong) }
        }
    }
}