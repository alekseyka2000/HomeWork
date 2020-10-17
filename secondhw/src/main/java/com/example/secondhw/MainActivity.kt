package com.example.secondhw

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.random

class MainActivity : AppCompatActivity() {

    private var setNumbers: MutableSet<Int> = mutableSetOf()

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.averageButton -> startSecondActivity()
            R.id.divideTheSetInHalfButton -> startSecondActivity()
            R.id.sumOfAllNumbersButton -> startSecondActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (members in 1..(8 * random()).toInt()) {
            setNumbers.add((random() * 9).toInt())
        }
        Log.d("Messages", "Set: ${setNumbers.toString()}")

        averageButton.setOnClickListener(clickListener)
        divideTheSetInHalfButton.setOnClickListener(clickListener)
        sumOfAllNumbersButton.setOnClickListener(clickListener)
    }

    private fun startSecondActivity() {

    }
}
