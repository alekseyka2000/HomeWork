package com.example.fourhw.observer

interface Observable {

    val observers: ArrayList<Observer>

    fun add(observer: Observer){
        observers.add(observer)
        observer.update()
    }

    fun remote(observer: Observer){
        observers.remove(observer)
    }

    fun sendUpdateEvent(){
        observers.forEach{it.update()}
    }
}