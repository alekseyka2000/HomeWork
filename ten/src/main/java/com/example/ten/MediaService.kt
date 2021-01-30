package com.example.ten


import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.concurrent.TimeUnit


interface ServiceActions {
    fun getData()
}

class MediaService : Service(), ServiceActions {

    private lateinit var ambientMediaPlayer: MediaPlayer

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

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG, "onCreate")
        ambientMediaPlayer=MediaPlayer.create(this, R.raw.m2)
        ambientMediaPlayer.isLooping = true
        ambientMediaPlayer.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotification()
        Log.d(LOG, "onStartCommand startId: $startId")
        someJob(startId)
        return START_NOT_STICKY
    }

    private fun someJob(startId: Int) {
        val runnable = Runnable {
            Log.d(LOG, "someJob started: $startId")
            TimeUnit.SECONDS.sleep(5)
            Log.d(LOG, "someJob sleep: $startId")
            stopSelf(4)
            Log.d(LOG, "someJob stopSelf: $startId")
        }
        Thread(runnable).start()
    }


    override fun onBind(intent: Intent?): IBinder = ServiceBinder()

    inner class ServiceBinder() : Binder() {
        fun getService(): MediaService {
            return this@MediaService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG, "onDestroy")
        ambientMediaPlayer.stop()
    }

    companion object {
        private val LOG = "ServiceExample"
        private val CHANNEL_ID = "ServiceExample"
    }

    override fun getData() {

    }
}