package com.example.ten

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat

interface ServiceActions {
    fun getCurrentSong(): String?
    fun setListener(clickListener: (Int) -> Unit)
}

class MediaService : Service(), ServiceActions {

    private var currentSong: Int = 0
    private val songList = R.raw::class.java.fields
    private lateinit var ambientMediaPlayer: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotification()

        songList.withIndex()
            .forEach { if (it.value.name == intent?.getStringExtra(SONG)) currentSong = it.index }
        startSong(currentSong)
        Log.d(LOG, "$currentSong")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG, "onDestroy")
        ambientMediaPlayer.stop()
    }

    private fun startSong(songIndex: Int) {
        songList[songIndex]?.name.let {
            val songID = resources.getIdentifier(it, "raw", packageName)
            ambientMediaPlayer = MediaPlayer.create(this, songID)
            ambientMediaPlayer.currentPosition
            if (ambientMediaPlayer.isPlaying) ambientMediaPlayer.reset() else ambientMediaPlayer.start()
        }
    }

    override fun getCurrentSong() = songList[currentSong]?.name

    override fun setListener(clickListener: (Int) -> Unit) {
        ambientMediaPlayer.setOnCompletionListener {
            clickListener(currentSong)
            startSong(if (currentSong == songList.size - 1) 0 else ++currentSong)
            if (ambientMediaPlayer.isPlaying) ambientMediaPlayer.reset()
        }
    }

    private fun createNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Example Service")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder = ServiceBinder()

    inner class ServiceBinder() : Binder() {
        fun getService(): MediaService {
            return this@MediaService
        }
    }

    companion object {
        private val LOG = "ServiceExample"
        private val CHANNEL_ID = "ServiceExample"
        val SONG = "SelectedSong"

        @JvmStatic
        fun getIntent(context: Context) = Intent(context, MediaService::class.java)

        @JvmStatic
        fun getIntent(context: Context, extra: String) = Intent(context, MediaService::class.java)
            .putExtra(SONG, extra)
    }
}