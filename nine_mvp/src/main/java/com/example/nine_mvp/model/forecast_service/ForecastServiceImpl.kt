package com.example.nine_mvp.model.forecast_service

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class ForecastServiceImpl : ForecastService {

    private val api = Retrofit.Builder()
        .baseUrl("https://samples.openweathermap.org/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    override fun getForecastData(city: String) = api.makeGetRequest(city)

}
