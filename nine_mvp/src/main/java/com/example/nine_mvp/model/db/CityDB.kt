package com.example.nine_mvp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nine_mvp.model.entity.CityForDB

@Database(entities = [CityForDB::class], version = 1, exportSchema = true)
abstract class CityDB : RoomDatabase() {
    abstract fun cityDAO(): CityDAO

    companion object{
        const val DATABASE_NAME = "city_database"

        @Volatile
        private var INSTANCE: CityDB? = null

        fun getDB(context: Context): CityDB {
            return INSTANCE ?: synchronized(this){
                val instance = (Room.databaseBuilder(
                    context.applicationContext,
                    CityDB::class.java,
                    DATABASE_NAME
                ).build() )
                INSTANCE = instance
                instance
            }
        }
    }
}