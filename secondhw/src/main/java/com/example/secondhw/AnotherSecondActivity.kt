package com.example.secondhw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import java.util.function.IntUnaryOperator

class AnotherSecondActivity : AppCompatActivity(){
    private lateinit var set: MutableSet<Int>

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.averageButton -> averageSet(set)
            R.id.divideTheSetInHalfButton -> divideSet(set)
            R.id.sumOfAllNumbersButton -> sumOfAllNumbersOfSet(set)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        /*averageButton.setOnClickListener(clickListener)
        divideTheSetInHalfButton.setOnClickListener(clickListener)
        sumOfAllNumbersButton.setOnClickListener(clickListener)*/
    }

    private fun averageSet(set: MutableSet<Int>) {
        //val average = set.average()
        Log.d("Message", "Average: ")
        finish()
    }

    private fun divideSet(set: MutableSet<Int>) {
        //val res = set.divide()
        finish()
    }

    private fun sumOfAllNumbersOfSet(set: MutableSet<Int>) {
        //val sum = set.sum().toDouble()
        Log.d("Message", "Sum: ")
        finish()
    }
}