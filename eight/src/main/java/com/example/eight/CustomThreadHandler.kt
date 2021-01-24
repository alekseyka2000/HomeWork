package com.example.eight

import android.os.Handler
import android.os.HandlerThread

class CustomThreadHandler(name: String) : HandlerThread(name) {

    private var handler: Handler

    init {
        start()
        handler = Handler(looper)
    }

    fun postTask(runnable: Runnable) {
        handler.post(runnable)
    }
}