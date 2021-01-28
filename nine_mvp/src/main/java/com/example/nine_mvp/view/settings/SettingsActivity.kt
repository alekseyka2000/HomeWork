package com.example.nine_mvp.view.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nine_mvp.R
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

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }
}