package com.example.seventh

import android.content.Context
import android.content.Intent
import com.example.seventh.DB.Contact
import com.example.seventh.DB.ContactDAO
import com.example.seventh.DB.ContactDB
import kotlinx.android.synthetic.main.activity_add_contact.*
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object TelephoneDirectory {

    private lateinit var dataBase: ContactDAO

    var personList = listOf( Contact(
    UUID.randomUUID().toString(),
    "PersonName",
    false,
    "contactEditText"))//listOf<Contact>()
    private val poolExecutors = Executors.newFixedThreadPool(5)
    private lateinit var mainExecutor: Executor

    fun openDB(context: Context) {
        dataBase = ContactDB.getDB(context).contactDAO()
        mainExecutor = context.mainExecutor
        getContactList()
    }

    fun addContact(contact: Contact) {
        CompletableFuture.runAsync { dataBase.insertContact(contact) }
        getContactList()
    }

    private fun getContactList() {
        CompletableFuture.supplyAsync {
            dataBase.getContactsList()
        }.thenApplyAsync { result ->
            personList = result
        }.thenRunAsync(Runnable { }, mainExecutor)
    }

    fun findContact(intent: Intent): Contact =
       personList.first { it.id == intent.getStringExtra("ID") }

    fun deleteContact(intent: Intent) {
//        CompletableFuture.runAsync({ dataBase.deleteContact(findContact(intent)) }, poolExecutors)
//            .thenRunAsync({ getContactList() }, mainExecutor)
    }

    fun editContact(oldContact: Contact, newContact: Contact) {
//        CompletableFuture.runAsync({ dataBase.editContact(oldContact, newContact) }, poolExecutors)
//            .thenRunAsync({ getContactList() }, mainExecutor)
    }
}