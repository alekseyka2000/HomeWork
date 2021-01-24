package com.example.nine_mvm.domain

import com.example.nine_mvm.model.entity.ConvertedForecastData
import io.reactivex.Single

interface ForecastDataConverter {
    fun getForecastData(city: String): Single<List<ConvertedForecastData>>
}