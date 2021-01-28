package com.example.nine_mvp.view.city_chooser

import androidx.lifecycle.LiveData

interface CityPresenter {
    fun getLiveData(): Pair<LiveData<List<String>>, LiveData<String>>
    fun getCityList()
    fun addCity(name: String)
    fun onCleared()
}