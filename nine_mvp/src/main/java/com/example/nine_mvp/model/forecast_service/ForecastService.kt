package com.example.nine_mvp.model.forecast_service

import com.example.nine_mvp.model.entity.ForecastData
import io.reactivex.Single

interface ForecastService {
    fun getForecastData(city: String): Single<ForecastData>
}