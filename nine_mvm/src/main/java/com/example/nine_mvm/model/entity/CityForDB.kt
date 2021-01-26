package com.example.nine_mvm.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityForDB(
    @PrimaryKey @ColumnInfo(name = "name") val cityName: String
)
