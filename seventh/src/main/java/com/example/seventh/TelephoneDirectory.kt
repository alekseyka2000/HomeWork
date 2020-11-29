package com.example.seventh

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.seventh.DB.Contact
import com.example.seventh.DB.ContactDAO
import com.example.seventh.DB.ContactDB
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.Executors

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
        CompletableFuture.runAsync({ dataBase.insertContact(contact) }, poolExecutors)
            .thenRunAsync({ getContactList() }, mainExecutor)
            .exceptionally { ex: Throwable -> showException(ex)}
    }

    private fun getContactList() {
        CompletableFuture.supplyAsync({
            dataBase.getContactsList()
        }, poolExecutors).thenApplyAsync({ result ->
            personList = result
            a.listContacts = result
        }, mainExecutor)
            .exceptionally { ex: Throwable -> showException(ex)}
    }

    fun findContact(intent: Intent): Contact =
        personList.first { it.id == intent.getStringExtra("ID") }

    fun deleteContact(intent: Intent) {
        CompletableFuture.runAsync({ dataBase.deleteContact(findContact(intent)) }, poolExecutors)
            .thenRunAsync({ getContactList() }, mainExecutor)
            .exceptionally { ex: Throwable -> showException(ex)}
    }

    fun editContact(oldContact: Contact, newContact: Contact) {
        CompletableFuture.runAsync({
            dataBase.editContact(
                oldContact.id,
                newContact.name,
                newContact.contact
            )
        }, poolExecutors)
            .thenRunAsync({ getContactList() }, mainExecutor)
            .exceptionally { ex: Throwable -> showException(ex)}
    }

    private fun showException(e: Throwable): Void? {
        Snackbar.make(mainView, e.message.toString(), Snackbar.LENGTH_LONG).show()
        return null
    }
}