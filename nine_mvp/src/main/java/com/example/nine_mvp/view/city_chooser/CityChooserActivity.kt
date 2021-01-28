package com.example.nine_mvp.view.city_chooser

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nine_mvp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject

class CityChooserActivity : AppCompatActivity() {

    private lateinit var cityListAdapter: CityListAdapter
    private val cityPresenter: CityPresenter by inject()
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_chooser)

        sharedPref = applicationContext.getSharedPreferences("Selected city", 0)
        var selectedCity = sharedPref.getString(getString(R.string.city_name), null)
        cityListAdapter = CityListAdapter(this, selectedCity) { a: String -> itemClick(a) }

        findViewById<RecyclerView>(R.id.cityRecycler).apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = cityListAdapter
        }

        findViewById<FloatingActionButton>(R.id.addCityButton).setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.add_city))
                .setView(this.layoutInflater.inflate(R.layout.alter_dialog_add_city, null))
                .setPositiveButton(resources.getString(R.string.positive_button_text)) { dialog, _ ->
                    val cityName =
                        (dialog as? AlertDialog)?.findViewById<EditText>(R.id.nameCityEditText)?.text.toString()
                    with(sharedPref.edit()) {
                        putString(getString(R.string.city_name), cityName)
                        apply()
                    }
                    selectedCity = cityName
                    cityPresenter.addCity(cityName)
                }
                .setNegativeButton(resources.getString(R.string.negative_button_text)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }

        val liveData = cityPresenter.getLiveData()
        liveData.first.observe(this, { data ->
            cityListAdapter.apply {
                updateList(cityList, data)
                cityList = data
            }
        })

        liveData.second.observe(this, { data ->
            Toast.makeText(this, data, Toast.LENGTH_LONG).show()
        })
    }

    private fun itemClick(name: String) {
        with(sharedPref.edit()) {
            putString(getString(R.string.city_name), name)
            apply()
        }
        finish()
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, CityChooserActivity::class.java)
    }
}