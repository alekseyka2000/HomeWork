package com.example.nine_mvm.domain

import android.content.Context
import com.example.nine_mvm.R
import com.example.nine_mvm.model.entity.ConvertedForecastData
import com.example.nine_mvm.model.forecast_service.ForecastService
import io.reactivex.Single

class ForecastDataConverterImpl(
    private val forecastService: ForecastService,
    private val appContext: Context
) : ForecastDataConverter {

    private val sharedPref =
        appContext.getSharedPreferences(appContext.getString(R.string.temperature_type), 0)

    override fun getForecastData(city: String): Single<List<ConvertedForecastData>> {
        return forecastService.getForecastData(city).map { data ->
            data.list.map { forecast ->
                ConvertedForecastData(
                    forecast.dtTxt.takeLast(8),
                    forecast.weather.first().description,
                    when (sharedPref.getBoolean(appContext.getString(R.string.temperature_type), true)) {
                        false -> (((forecast.main.temp - 273.15) * 9 / 5).toInt() + 32).toInt().toString()+"°F"
                        true -> (forecast.main.temp - 273.15).toInt().toString()+"℃"
                    }
                )
            }
        }
    }
}