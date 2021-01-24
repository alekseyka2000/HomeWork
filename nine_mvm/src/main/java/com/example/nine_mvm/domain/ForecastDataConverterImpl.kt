package com.example.nine_mvm.domain

import android.content.Context
import com.example.nine_mvm.R
import com.example.nine_mvm.model.entity.ConvertedForecastData
import com.example.nine_mvm.model.forecast_service.ForecastService

class ForecastDataConverterImpl(
    private val forecastService: ForecastService,
    private val appContext: Context
) : ForecastDataConverter {

    private val sharedPref =
        appContext.getSharedPreferences(appContext.getString(R.string.temperature_type), 0)

    override fun getForecastData(city: String) {
        forecastService.getForecastData(city).map { data ->
            data.list.map { forecast ->
                ConvertedForecastData(
                    forecast.dt_txt.takeLast(8),
                    forecast.weather.first().description,
                    when (sharedPref.getInt(appContext.getString(R.string.temperature_type), 1)) {
                        2 -> (((forecast.main.temp - 273.15) * 9 / 5).toInt() + 32).toString()
                        else -> (forecast.main.temp - 273.15).toString()
                    }
                )
            }
        }
    }
}