package com.example.nine_mvm.model

import com.example.nine_mvm.model.db.CityDB
import com.example.nine_mvm.model.entity.CityForDB

class CityRepoImpl(private val db: CityDB) : CityRepo {
    override fun getCityData() = db.cityDAO().getDataList()

    override fun addCity(name: String) {
        db.cityDAO().insertData(CityForDB(name))
    }
}