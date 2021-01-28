package com.example.nine_mvm.view.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nine_mvm.R
import com.example.nine_mvm.view.city_chooser.CityChooserActivity
import com.example.nine_mvm.view.settings.SettingsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private var selectedCity: String? = null
    private lateinit var sharedPref: SharedPreferences
    private lateinit var forecastListAdapter: ForecastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = applicationContext.getSharedPreferences("Selected city", 0)

        forecastListAdapter = ForecastListAdapter(this)

        findViewById<RecyclerView>(R.id.weatherRecyclerView).apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = forecastListAdapter
        }

        mainViewModel.forecastLiveData.observe(this, { listData ->
            findViewById<TextView>(R.id.temperatureTextView).text = listData.first().temperature
            findViewById<TextView>(R.id.weatherDescriptionTextView).text = listData.first().description
            Log.d("tags", "$listData")
            forecastListAdapter.apply {
                updateList(forecastList, listData)
                forecastList = listData
            }
        })

        mainViewModel.messageLiveData.observe(this, { data ->
            Toast.makeText(this, data, Toast.LENGTH_LONG).show()
        })

        findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            startActivity(SettingsActivity.getIntent(this))
        }

        findViewById<FloatingActionButton>(R.id.cityChooserButton).setOnClickListener {
            startActivity(CityChooserActivity.getIntent(this))
        }
    }

    override fun onStart() {
        super.onStart()

        selectedCity = sharedPref.getString(getString(R.string.city_name), null)
        selectedCity?.let { mainViewModel.getData(it) }
        findViewById<TextView>(R.id.cityNameTextView).text = selectedCity
    }
}