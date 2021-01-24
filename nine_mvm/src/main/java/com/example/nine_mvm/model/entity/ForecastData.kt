package com.example.nine_mvm.model.entity

import com.example.nine_mvm.model.forecast_service.Forecast

data class ForecastData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Double
)