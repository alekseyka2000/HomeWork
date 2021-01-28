package com.example.nine_mvm.model.forecast_service

import com.example.nine_mvm.model.entity.ForecastData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("data/2.5/forecast?appid=439d4b804bc8187953eb36d2a8c26a02")
    fun makeGetRequest(
        @Query("q")city: String
    ): Single<ForecastData>

}