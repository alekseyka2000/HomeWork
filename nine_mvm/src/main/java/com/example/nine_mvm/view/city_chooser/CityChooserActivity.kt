package com.example.nine_mvm.view.city_chooser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nine_mvm.R

class CityChooserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_chooser)
    }

    companion object{
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, CityChooserActivity::class.java)
    }
}