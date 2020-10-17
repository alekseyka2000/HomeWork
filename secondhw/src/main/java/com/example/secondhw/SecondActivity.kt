package com.example.secondhw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity: AppCompatActivity() {

    private lateinit var set: MutableSet<Int>
    private lateinit var intentForRes: Intent

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
        intentForRes = Intent(this, MainActivity::class.java)

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
        Log.d("Message", "Average: ")
        intentForRes.putExtra("Res", average)
        setResult(Activity.RESULT_OK, intentForRes)
        finish()
    }

    private fun divideSet(set: MutableSet<Int>) {
        val res = set.divide()
        if (res!= 0.0) {
            intentForRes.putExtra("Res", res)
            setResult(Activity.RESULT_OK, intentForRes)
        }
        finish()
    }

    private fun sumOfAllNumbersOfSet(set: MutableSet<Int>) {
        val sum = set.sum().toDouble()
        Log.d("Message", "Sum: ")
        intentForRes.putExtra("Res", sum)
        setResult(Activity.RESULT_OK, intentForRes)
        finish()
    }
}

