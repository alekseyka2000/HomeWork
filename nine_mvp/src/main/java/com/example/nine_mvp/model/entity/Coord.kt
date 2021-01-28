package com.example.nine_mvp.model.entity

import com.google.gson.annotations.SerializedName

data class Coord(

    @SerializedName("lat") val lat : Double,
    @SerializedName("lon") val lon : Double
)