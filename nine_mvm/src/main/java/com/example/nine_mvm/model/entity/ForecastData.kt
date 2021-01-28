package com.example.nine_mvm.model.entity

import com.google.gson.annotations.SerializedName

data class ForecastData(
    @SerializedName("cod") val cod : Int,
    @SerializedName("message") val message : Double,
    @SerializedName("cnt") val cnt : Int,
    @SerializedName("list") val list : List<Forecast>,
    @SerializedName("city") val city : City
)