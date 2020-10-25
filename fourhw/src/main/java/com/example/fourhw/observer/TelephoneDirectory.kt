package com.example.fourhw.observer

object TelephoneDirectory : Observable {

    override val observers: ArrayList<Observer> = ArrayList()
    var personList = mutableListOf<Person>()

    var person: Person? = null
        set(value) {
            field = value
            value?.let { personList.add(it)}
            sendUpdateEvent()
        }
}