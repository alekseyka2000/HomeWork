package com.example.nine_mvm.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.nine_mvm.R
import com.example.nine_mvm.view.city_chooser.CityChooserActivity
import com.example.nine_mvm.view.settings.SettingsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.settingsButton).setOnClickListener{
            startActivity(SettingsActivity.getIntent(this))
        }

        findViewById<FloatingActionButton>(R.id.cityChooserButton).setOnClickListener{
            startActivity(CityChooserActivity.getIntent(this))
        }
    }
}