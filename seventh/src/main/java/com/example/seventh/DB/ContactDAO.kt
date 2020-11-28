package com.example.seventh.DB

import androidx.room.*

@Dao
interface ContactDAO {

    @Query("SELECT * FROM CONTACT_TABLE")
    fun getContactsList(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("UPDATE CONTACT_TABLE SET person_name = :newContact  WHERE person_name = :oldContact")
    fun editContact(oldContact: String, newContact: String)
}