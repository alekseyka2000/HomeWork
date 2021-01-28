package com.example.nine_mvm.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nine_mvm.domain.ForecastDataConverter
import com.example.nine_mvm.model.entity.ConvertedForecastData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val forecastDataConverter: ForecastDataConverter) : ViewModel() {

    private val subscriber = CompositeDisposable()
    private val forecastMutableLiveData = MutableLiveData<List<ConvertedForecastData>>()
    private val messageMutableLiveData = MutableLiveData<String>()
    val forecastLiveData: LiveData<List<ConvertedForecastData>> = forecastMutableLiveData
    val messageLiveData: LiveData<String> = messageMutableLiveData

    fun getData(city: String) {
        subscriber.add(
            forecastDataConverter.getForecastData(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ forecastMutableLiveData.value = it },
                    { messageMutableLiveData.value = it.message })
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriber.clear()
    }
}