package com.example.seventh

import android.content.Intent
import java.util.UUID

object TelephoneDirectory {

    var personList = mutableListOf<Person>()

    init {
        addContact(Person(UUID.randomUUID().toString(),
            "Vasya",
            false,
            "+37529***"))
        addContact(Person(UUID.randomUUID().toString(),
            "Vova",
            false,
            "+37533**"))
        addContact(Person(UUID.randomUUID().toString(),
            "Lena",
            true,
            "lena@gmail.com"))

        addContact(Person(UUID.randomUUID().toString(),
            "Vasya",
            false,
            "+37529***"))
        addContact(Person(UUID.randomUUID().toString(),
            "Vova",
            false,
            "+37533**"))
        addContact(Person(UUID.randomUUID().toString(),
            "Lena",
            true,
            "lena@gmail.com"))

        addContact(Person(UUID.randomUUID().toString(),
            "Vasya",
            false,
            "+37529***"))
        addContact(Person(UUID.randomUUID().toString(),
            "Vova",
            false,
            "+37533**"))
        addContact(Person(UUID.randomUUID().toString(),
            "Lena",
            true,
            "lena@gmail.com"))
    }

    fun addContact(person: Person) {
        personList.add(person)
    }

    fun findContact(intent: Intent): Person =
        personList.first { it.id == intent.getStringExtra("ID") }

    fun getIndex(intent: Intent): Int =
        personList.indexOfFirst { it.id == intent.getStringExtra("ID") }

    fun deleteContact(intent: Intent) {
        personList.removeAt(getIndex(intent))
    }
}