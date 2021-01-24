package com.example.eight.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDAO {

    @Query("SELECT * FROM CONTACT_TABLE")
    fun getContactsList(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Query("DELETE FROM CONTACT_TABLE WHERE id = :id")
    fun deleteContact(id: String)

    @Query("UPDATE CONTACT_TABLE SET person_name = :newContactName, contact = :newContact  WHERE id = :oldContact")
    fun editContact(oldContact: String, newContactName: String, newContact: String)
}