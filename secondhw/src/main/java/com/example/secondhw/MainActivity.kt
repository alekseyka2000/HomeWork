package com.example.secondhw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.random


class MainActivity : AppCompatActivity(), IObserver {

    private lateinit var intentForRes: Intent
    private val requestCode = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Storage.add(this)

        startSecondActivityButton.setOnClickListener {
            intentForRes = Intent(this, SecondActivity::class.java)
            val setNumbers: MutableSet<Int> = mutableSetOf()
            setNumbers.addAll(getRandomSet(setNumbers))
            val name = setNumbers.toIntArray()
            intentForRes.putExtra("set", name)
            startActivityForResult(intentForRes, requestCode)
        }

        startAnotherSecondActivityButton.setOnClickListener {
            intentForRes = Intent(this, AnotherSecondActivity::class.java)
            val setNumbers: MutableSet<Int> = mutableSetOf()
            setNumbers.addAll(getRandomSet(setNumbers))
            Storage.set = setNumbers
            startActivity(intentForRes)
        }
    }

    private fun getRandomSet(setNumbers: MutableSet<Int>): MutableSet<Int> {
        for (members in 1..(8 * random()).toInt()) {
            setNumbers.add((random() * 9).toInt())
        }
        Log.d("Messages", "Set: $setNumbers")
        return setNumbers
    }

    override fun onDestroy() {
        super.onDestroy()
        Storage.remote(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            this.requestCode -> if (resultCode == Activity.RESULT_OK) {
                val res = data?.getDoubleExtra("Res", -10000.0)
                if (res != -10000.0) Log.d("Messages", "$res")
                else Log.d("Messages", "Problem with result")
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun update() {}

    override fun updateRes() {
        if (Storage.result != -1000.0) Log.d("Messages", "${Storage.result}")
    }
}