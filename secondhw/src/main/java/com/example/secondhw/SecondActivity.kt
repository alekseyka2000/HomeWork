package com.example.secondhw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity: AppCompatActivity() {

    private lateinit var set: MutableSet<Int>

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.averageButton -> averageSet()
            R.id.divideTheSetInHalfButton -> divideSet()
            R.id.sumOfAllNumbersButton -> sumOfAllNumbersOfSet()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        set = intent.getIntArrayExtra("set")?.toSet() as MutableSet<Int>
        Log.d("Messages", "Set: $set")

        averageButton.setOnClickListener(clickListener)
        divideTheSetInHalfButton.setOnClickListener(clickListener)
        sumOfAllNumbersButton.setOnClickListener(clickListener)
    }

    private fun averageSet() {
        finish()
    }

    private fun divideSet() {
        finish()
    }

    private fun sumOfAllNumbersOfSet() {
        finish()
    }
}

