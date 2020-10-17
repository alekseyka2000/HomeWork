package com.example.secondhw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.random


class MainActivity : AppCompatActivity() {

    private lateinit var intentForRes: Intent
    private val REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startSecondActivityButton.setOnClickListener {
            intentForRes = Intent(this, SecondActivity::class.java)
            val setNumbers: MutableSet<Int> = mutableSetOf()

            for (members in 1..(8 * random()).toInt()) {
                setNumbers.add((random() * 9).toInt())
            }
            Log.d("Messages", "Set: $setNumbers")

            val name = setNumbers.toIntArray()
            intentForRes.putExtra("set", name)
            startActivityForResult(intentForRes, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                val res = data?.getDoubleExtra("Res",-10000.0)
                if (res != -10000.0)Log.d("Messages", "$res")
                else Log.d("Messages", "Problem with result")
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}