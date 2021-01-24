package com.example.eight.model

import com.example.eight.model.db.Contact

interface ContactRepository {

    fun addContact(contact: Contact)

    fun getContactList(): List<Contact>

    fun deleteContact(id: String)

    fun editContact(contact: Contact)
}