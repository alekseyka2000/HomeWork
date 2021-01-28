package com.example.nine_mvp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nine_mvp.domain.ForecastDataConverter
import com.example.nine_mvp.model.entity.ConvertedForecastData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(private val forecastDataConverter: ForecastDataConverter) : MainPresenter {

    private val subscriber = CompositeDisposable()
    private val forecastMutableLiveData = MutableLiveData<List<ConvertedForecastData>>()
    private val messageMutableLiveData = MutableLiveData<String>()
    val forecastLiveData: LiveData<List<ConvertedForecastData>> = forecastMutableLiveData
    val messageLiveData: LiveData<String> = messageMutableLiveData

    override fun getLiveData() = Pair(forecastLiveData, messageLiveData)

    override fun getData(city: String) {
        subscriber.add(
            forecastDataConverter.getForecastData(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ forecastMutableLiveData.value = it },
                    { messageMutableLiveData.value = it.message })
        )
    }

    override fun onCleared() {
        subscriber.clear()
    }
}