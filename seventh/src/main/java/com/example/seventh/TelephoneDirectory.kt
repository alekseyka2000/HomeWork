package com.example.seventh

import android.content.Context
import android.content.Intent
import com.example.seventh.DB.Contact
import com.example.seventh.DB.ContactDAO
import com.example.seventh.DB.ContactDB
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object TelephoneDirectory {

    private lateinit var dataBase: ContactDAO

    var personList = listOf<Contact>()

    private lateinit var mainExecutor: Executor
    private lateinit var a: RecyclerAdapter
    private val poolExecutors = Executors.newFixedThreadPool(5)

    fun openDB(context: Context, adapter: RecyclerAdapter) {
        dataBase = ContactDB.getDB(context).contactDAO()
        mainExecutor = context.mainExecutor
        a = adapter
        getContactList()
    }

    fun addContact(contact: Contact) {
        CompletableFuture.runAsync({ dataBase.insertContact(contact) }, poolExecutors)
            .thenRunAsync({ getContactList() }, mainExecutor)
    }

    private fun getContactList() {
        CompletableFuture.supplyAsync({
            dataBase.getContactsList()
        }, poolExecutors).thenApplyAsync({ result ->
            personList = result
            a.listContacts = result
        }, mainExecutor)

    }

    fun findContact(intent: Intent): Contact =
        personList.first { it.id == intent.getStringExtra("ID") }

    fun deleteContact(intent: Intent) {
        CompletableFuture.runAsync({ dataBase.deleteContact(findContact(intent)) }, poolExecutors)
            .thenRunAsync({ getContactList() }, mainExecutor)
    }

    fun editContact(oldContact: Contact, newContact: Contact) {
        CompletableFuture.runAsync({ dataBase.editContact(oldContact.id, newContact.name, newContact.contact) }, poolExecutors)
            .thenRunAsync({ getContactList() }, mainExecutor)
    }
}