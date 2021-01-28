package com.example.nine_mvp.model

import com.example.nine_mvp.model.db.CityDB
import com.example.nine_mvp.model.entity.CityForDB

class CityRepoImpl(private val db: CityDB) : CityRepo {
    override fun getCityData() = db.cityDAO().getDataList()

    override fun addCity(name: String) {
        db.cityDAO().insertData(CityForDB(name))
    }
}