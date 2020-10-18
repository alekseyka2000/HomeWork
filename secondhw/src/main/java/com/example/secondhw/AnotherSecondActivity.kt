package com.example.secondhw

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class AnotherSecondActivity : AppCompatActivity(), IObserver {
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

        Storage.add(this)
        if (set.isEmpty()) {
            Log.d("Message", "Empty set")
            finish()
        }

        averageButton.setOnClickListener(clickListener)
        divideTheSetInHalfButton.setOnClickListener(clickListener)
        sumOfAllNumbersButton.setOnClickListener(clickListener)
    }

    override fun onDestroy() {
        super.onDestroy()

        Storage.remote(this)
    }

    override fun update() {
        set = Storage.set
    }

    override fun updateRes() {}

    private fun averageSet(set: MutableSet<Int>) {
        val average = set.average()
        Log.d("Message", "Average: ")
        Storage.result = average
        finish()
    }

    private fun divideSet(set: MutableSet<Int>) {
        val res = set.divide()
        if (res != -1000.0) {
            Storage.result = res
        }
        finish()
    }

    private fun sumOfAllNumbersOfSet(set: MutableSet<Int>) {
        val sum = set.sum().toDouble()
        Log.d("Message", "Sum: ")
        Storage.result = sum
        finish()
    }
}