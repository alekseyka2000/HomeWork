package com.example.fourhw

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


}