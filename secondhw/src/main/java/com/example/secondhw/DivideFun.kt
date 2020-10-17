package com.example.secondhw

import android.util.Log

fun MutableSet<Int>.divide(): Double {
    var result = 0.0
    if (this.size % 2 == 0) {
        val set1 = mutableSetOf<Int>()
        val set2 = mutableSetOf<Int>()
        this.forEach { element ->
            if (this.indexOf(element) < this.size / 2) {
                set1.add(element)
            } else {
                set2.add(element)
            }
        }
        if (set2.sum() != 0){
            result = ((set1.sum().toDouble() / set2.sum().toDouble() * -1))
            Log.d("Message", "Divide result: ")
            return result
        }
        else Log.d("Message", "Error, sum of second part set = 0")
    } else {
        Log.d("Message", "The set has an odd number of elements")
    }
    return result
}