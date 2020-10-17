package com.example.secondhw

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity: AppCompatActivity() {

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

        if (intent.getIntArrayExtra("set")?.toSet()?.isNotEmpty()!!) {
            set = intent.getIntArrayExtra("set")?.toSet() as MutableSet<Int>
        }else{
            Log.d("Message", "Empty set")
            finish()
        }

        averageButton.setOnClickListener(clickListener)
        divideTheSetInHalfButton.setOnClickListener(clickListener)
        sumOfAllNumbersButton.setOnClickListener(clickListener)
    }

    private fun averageSet(set: MutableSet<Int>) {
        val average = set.average()
        Log.d("Message", "Average: $average")
        finish()
    }

    private fun divideSet(set: MutableSet<Int>) {
        set.divide()
        finish()
    }

    private fun sumOfAllNumbersOfSet(set: MutableSet<Int>) {
        val sum = set.sum()
        Log.d("Message", "Sum: $sum")
        finish()
    }
}

