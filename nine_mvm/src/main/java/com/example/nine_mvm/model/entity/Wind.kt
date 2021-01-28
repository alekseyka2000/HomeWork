package com.example.nine_mvm.model.entity

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") val speed : Double,
    @SerializedName("deg") val deg : Double
)