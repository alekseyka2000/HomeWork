package com.example.nine_mvm.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nine_mvm.domain.ForecastDataConverter
import com.example.nine_mvm.model.entity.ConvertedForecastData
import io.reactivex.Single

class MainViewModel(private val forecastDataConverter: ForecastDataConverter): ViewModel() {

    private val forecastMutableLiveData = MutableLiveData<List<ConvertedForecastData>>()
    val forecastLiveData: LiveData<List<ConvertedForecastData>> = forecastMutableLiveData

    private fun getData(city: String) {
        forecastDataConverter.getForecastData(city)
    }
}