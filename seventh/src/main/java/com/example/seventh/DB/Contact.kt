package com.example.seventh.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
class Contact (
    @PrimaryKey @ColumnInfo(name = "ID") val id: String,
    @ColumnInfo(name = "person_name") val name: String,
    @ColumnInfo(name = "contact_type") val contactType: Boolean,
    @ColumnInfo(name = "contact") val contact: String
)