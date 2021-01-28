package com.example.nine_mvp.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nine_mvp.model.entity.CityForDB
import io.reactivex.Observable

@Dao
interface CityDAO {

    @Query("SELECT * FROM city")
    fun getDataList(): Observable<List<CityForDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(city: CityForDB)
}