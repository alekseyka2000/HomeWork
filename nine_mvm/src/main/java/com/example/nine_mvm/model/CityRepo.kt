package com.example.nine_mvm.model

import com.example.nine_mvm.model.entity.CityForDB
import io.reactivex.Observable

interface CityRepo {

    fun getCityData(): Observable<List<CityForDB>>
    fun addCity(name: String)
}