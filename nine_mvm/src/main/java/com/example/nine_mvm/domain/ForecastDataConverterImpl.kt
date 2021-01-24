package com.example.nine_mvm.domain

import com.example.nine_mvm.model.forecast_service.ForecastService

class ForecastDataConverterImpl(
    private val forecastService: ForecastService
): ForecastDataConverter {
    override fun getForecastData(city: String){
        forecastService.getForecastData(city)
            .map {  }
    }
}