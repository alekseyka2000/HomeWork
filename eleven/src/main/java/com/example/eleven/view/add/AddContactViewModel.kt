package com.example.eleven.view.add

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eleven.CustomThreadHandler
import com.example.eleven.R
import com.example.eleven.model.ContactRepository
import com.example.eleven.model.db.Contact
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class AddContactViewModel(
    private val repository: ContactRepository,
    private val contextApp: Context
) : ViewModel() {

    private val poolExecutors = Executors.newFixedThreadPool(5)
    private val sharedPref = contextApp.getSharedPreferences("Type concurrent", 0)
    private val customHandlerThread by lazy { CustomThreadHandler("Custom handler") }
    private val handler = Handler(contextApp.mainLooper)

    fun addContactToList(contact: Contact) {
        when (sharedPref.getInt(contextApp.getString(R.string.type_concurrent), 1)) {
            1 -> {
                CompletableFuture.runAsync(Runnable{ repository.addContact(contact) }, poolExecutors)
                    .exceptionally { ex: Throwable ->
                        Log.d("TAG", "${ex.message}")
                        null
                    }
            }
            2 -> {
                Single.just(repository).map { it.addContact(contact) }
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.d("TAG", "RXJAVA") },
                        { Log.d("TAG", "${it.message}") }
                    )
            }
            3 -> {
                customHandlerThread.apply {
                    postTask {
                        repository.addContact(contact)
                        handler.post { Log.d("TAG", "Handler") }
                    }
                }
            }
            else -> {
                Log.d("TAG", "Not correct choose type concurrent")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        customHandlerThread.interrupt()
    }
}