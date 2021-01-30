package com.example.eleven.model

import com.example.eleven.model.db.Contact

interface ContactRepository {

    fun addContact(contact: Contact)

    fun getContactList(): List<Contact>

    fun deleteContact(id: String)

    fun editContact(contact: Contact)
}