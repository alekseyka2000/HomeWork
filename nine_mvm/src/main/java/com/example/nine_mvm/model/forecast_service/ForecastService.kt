package com.example.nine_mvm.model.forecast_service

import com.example.nine_mvm.model.entity.ForecastData
import io.reactivex.Single

interface ForecastService {
    fun getForecastData(city: String): Single<ForecastData>
}