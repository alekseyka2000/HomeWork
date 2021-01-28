package com.example.nine_mvp.model

import com.example.nine_mvp.model.entity.CityForDB
import io.reactivex.Observable

interface CityRepo {

    fun getCityData(): Observable<List<CityForDB>>
    fun addCity(name: String)
}