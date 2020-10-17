package com.example.secondhw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.random
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var intentForRes: Intent
    private val requestCode = 1000

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
            startActivityForResult(intentForRes, requestCode)
        }
    }

}
