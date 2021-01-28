package com.example.nine_mvp.view.main

import androidx.lifecycle.LiveData
import com.example.nine_mvp.model.entity.ConvertedForecastData

interface MainPresenter {
    fun getLiveData(): Pair<LiveData<List<ConvertedForecastData>>, LiveData<String>>
    fun getData(city: String)
    fun onCleared()
}