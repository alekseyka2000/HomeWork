package com.example.secondhw

interface IObservable {
    val observers: ArrayList<IObserver>

    fun add(observer: IObserver) {
        observers.add(observer)
        observer.update()
        observer.updateRes()
    }

    fun remote(observer: IObserver) {
        observers.remove(observer)
    }

    fun sendUpdateEvent() {
        observers.forEach { it.update() }
    }

    fun sendUpdateResEvent() {
        observers.forEach { it.updateRes() }
    }
}