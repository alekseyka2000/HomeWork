package com.example.secondhw

import android.util.Log

fun MutableSet<Int>.divide(){
    if (this.size%2==0){
        val set1 = mutableSetOf<Int>()
        val set2 = mutableSetOf<Int>()
        this.forEach { element ->
            if (this.indexOf(element)<this.size/2){
                set1.add(element)
            }else{
                set2.add(element)
            }
        }
        if (set2.sum()!=0) set1.sum()/set2.sum()*-1
        else Log.d("Message", "Error, sum of second part set = 0")
        Log.d("Message", "Sum: $set1,   $set2")
    }else {
        Log.d("Message", "The set has an odd number of elements")
    }
}