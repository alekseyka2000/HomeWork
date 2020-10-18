package com.example.secondhw

object Storage : IObservable {
    override val observers: ArrayList<IObserver> = ArrayList()

    var set = mutableSetOf<Int>()
        set(value) {
            field = value
            sendUpdateEvent()
        }

    var result = -1000.0
        set(value) {
            field = value
            sendUpdateResEvent()
        }
}