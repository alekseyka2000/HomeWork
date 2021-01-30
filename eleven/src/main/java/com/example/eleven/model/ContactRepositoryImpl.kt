package com.example.eleven.model

import com.example.eleven.model.db.Contact
import com.example.eleven.model.db.ContactDAO

class ContactRepositoryImpl(private val dataBase: ContactDAO) : ContactRepository {
    override fun addContact(contact: Contact) {
        dataBase.insertContact(contact)
    }

    override fun getContactList() = dataBase.getContactsList()

    override fun deleteContact(id: String) {
        dataBase.deleteContact(id)
    }

    override fun editContact(contact: Contact) {
        dataBase.editContact(contact.id, contact.name, contact.contact)
    }
}