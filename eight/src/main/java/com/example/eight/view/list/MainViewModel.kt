package com.example.eight.view.list

import android.content.Context
import android.os.Handler
import io.reactivex.android.schedulers.AndroidSchedulers
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eight.CustomThreadHandler
import com.example.eight.R
import com.example.eight.model.ContactRepository
import com.example.eight.model.db.Contact
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class MainViewModel(
    private val repository: ContactRepository,
    private val contextApp: Context
) : ViewModel() {

    private val mainExecutor = contextApp.mainExecutor
    private val poolExecutors = Executors.newFixedThreadPool(5)
    private val sharedPref =
        contextApp.getSharedPreferences(contextApp.getString(R.string.type_concurrent), 0)
    private val mutableContactsLiveData = MutableLiveData<List<Contact>>()
    val contactListLiveData: LiveData<List<Contact>> = mutableContactsLiveData
    private val customHandlerThread by lazy { CustomThreadHandler("Custom handler") }
    private val handler = Handler(contextApp.mainLooper)

    fun getContactList() {
        when (sharedPref.getInt(contextApp.getString(R.string.type_concurrent), 1)) {
            1 -> {
                CompletableFuture.supplyAsync({ repository.getContactList() }, poolExecutors)
                    .thenApplyAsync({ result ->
                        Log.d("TAG", "CF")
                        mutableContactsLiveData.value = result
                    }, mainExecutor)
                    .exceptionally { ex: Throwable -> Log.d("TAG", "${ex.message}") }
            }
            2 -> {
                Single.just(repository).map { it.getContactList() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            mutableContactsLiveData.value = result
                            Log.d("TAG", "RXJAVA")
                        },
                        { Log.d("TAG", "${it.message}") }
                    )
            }
            3 -> {
                customHandlerThread.apply {
                    postTask {
                        val result = repository.getContactList()
                        handler.post {
                            mutableContactsLiveData.value = result
                            Log.d("TAG", "Handler")
                        }
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