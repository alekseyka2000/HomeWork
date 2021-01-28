package com.example.nine_mvp.domain

import com.example.nine_mvp.model.entity.ConvertedForecastData
import io.reactivex.Single

interface ForecastDataConverter {
    fun getForecastData(city: String): Single<List<ConvertedForecastData>>
}