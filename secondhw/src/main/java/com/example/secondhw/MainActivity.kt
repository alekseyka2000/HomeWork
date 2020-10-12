package com.example.secondhw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.Math.random

class MainActivity : AppCompatActivity() {

    var setNumbers: MutableSet<Int> = mutableSetOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (members in 1..(8*random()).toInt()){
           setNumbers.add((random()*9).toInt())
        }
        Log.d("Messages", "Set: ${setNumbers.toString()}")
    }
}
