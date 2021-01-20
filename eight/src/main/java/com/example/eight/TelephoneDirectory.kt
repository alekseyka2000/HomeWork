package com.example.eight

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.eight.db.Contact
import com.example.eight.db.ContactDAO
import com.example.eight.db.ContactDB
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.function.Function
import java.util.function.Supplier

object TelephoneDirectory {

    private lateinit var dataBase: ContactDAO

    var personList = listOf<Contact>()

    private lateinit var mainExecutor: Executor
    lateinit var mainView: View
    private lateinit var a: RecyclerAdapter
    private val poolExecutors = Executors.newFixedThreadPool(5)

    fun openDB(context: Context, adapter: RecyclerAdapter, view: View) {
        dataBase = ContactDB.getDB(context).contactDAO()
        mainExecutor = context.mainExecutor
        a = adapter
        mainView = view
        getContactList()
    }

    fun addContact(contact: Contact) {
        CompletableFuture.runAsync(Runnable{ dataBase.insertContact(contact) }, poolExecutors)
            .thenRunAsync(Runnable{ getContactList() }, mainExecutor)
            .exceptionally { ex: Throwable -> showException(ex)}
    }

    private fun getContactList() {
        CompletableFuture.supplyAsync(Supplier{
            dataBase.getContactsList()
        }, poolExecutors).thenApplyAsync(Function<List<Contact>, Boolean>{ result ->
            personList = result
            a.listContacts = result
            true
        }, mainExecutor)
    }

    fun findContact(intent: Intent): Contact =
        personList.first { it.id == intent.getStringExtra("ID") }

    fun deleteContact(intent: Intent) {
        CompletableFuture.runAsync(Runnable{ dataBase.deleteContact(findContact(intent)) }, poolExecutors)
            .thenRunAsync(Runnable{ getContactList() }, mainExecutor)
            .exceptionally { ex: Throwable -> showException(ex)}
    }

    fun editContact(oldContact: Contact, newContact: Contact) {
        CompletableFuture.runAsync(Runnable{
            dataBase.editContact(
                oldContact.id,
                newContact.name,
                newContact.contact
            )
        }, poolExecutors)
            .thenRunAsync(Runnable{ getContactList() }, mainExecutor)
            .exceptionally { ex: Throwable -> showException(ex)}
    }

    private fun showException(e: Throwable): Void? {
        Snackbar.make(mainView, e.message.toString(), Snackbar.LENGTH_LONG).show()
        return null
    }
}