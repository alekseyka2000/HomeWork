package com.example.nine_mvm.model.forecast_service

import com.example.nine_mvm.model.entity.*

data class Forecast(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val rain: Rain,
    val snow: Snow,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)