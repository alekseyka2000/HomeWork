package com.example.nine_mvm.view.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nine_mvm.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = applicationContext.getSharedPreferences("Type concurrent", 0)
        setContentView(R.layout.activity_settings)
        findViewById<SwitchMaterial>(R.id.cesiusSwitch).setOnCheckedChangeListener{ _ , isChecked ->
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.temperature_type), isChecked)
                apply()
            }
        }
    }
}