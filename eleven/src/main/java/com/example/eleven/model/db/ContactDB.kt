package com.example.eleven.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDB : RoomDatabase() {
    abstract fun contactDAO(): ContactDAO

    companion object{
        @Volatile
        private var INSTANCE: ContactDB? = null

        fun getDB(context: Context): ContactDB {
            return INSTANCE ?: synchronized(this){
                INSTANCE = (Room.databaseBuilder(
                    context.applicationContext,
                    ContactDB::class.java,
                    "contact_database"
                ).build() )
                INSTANCE as ContactDB
            }
        }
    }
}