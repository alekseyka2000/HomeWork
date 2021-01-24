package com.example.eight.view.list

import android.content.SharedPreferences
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eight.R
import com.example.eight.model.db.Contact
import com.example.eight.view.CellClickListener
import com.example.eight.view.RecyclerAdapter
import com.example.eight.view.add.AddContactActivity
import com.example.eight.view.edit.EditContactActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), CellClickListener {

    private val contactAdapter = RecyclerAdapter(this@MainActivity)
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = applicationContext.getSharedPreferences("Type concurrent", 0)

        buttonAdd.setOnClickListener {
            startActivity(AddContactActivity.getIntent(this))
        }

        recyclerView.apply {
            layoutManager = if (resources.configuration.orientation == ORIENTATION_PORTRAIT)
                LinearLayoutManager(this@MainActivity)
            else GridLayoutManager(this@MainActivity, 2)
            adapter = contactAdapter
        }

        mainViewModel.contactListLiveData.observe(this, { contactsList ->
            contactAdapter.listContacts = contactsList
        })

        searchText.doAfterTextChanged {
            val subtext = it.toString()
            contactAdapter.listContacts = mainViewModel.contactListLiveData.value?.filter { list ->
                list.name.contains(subtext, true)
                    .or(list.contact.contains(subtext, true))
            } ?: emptyList()
        }

        mainRadioGroup.check(
            when (sharedPref.getInt(applicationContext.getString(R.string.type_concurrent), 1)) {
                2 -> R.id.rxjava
                3 -> R.id.handler
                else -> R.id.completableFuture
            }
        )

        mainRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.completableFuture -> {
                    with(sharedPref.edit()) {
                        putInt(getString(R.string.type_concurrent), 1)
                        apply()
                    }
                }
                R.id.rxjava -> {
                    with(sharedPref.edit()) {
                        putInt(getString(R.string.type_concurrent), 2)
                        apply()
                    }
                }
                R.id.handler -> {
                    with(sharedPref.edit()) {
                        putInt(getString(R.string.type_concurrent), 3)
                        apply()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        searchText.text = null
        mainViewModel.getContactList()
    }

    override fun onCellClickListener(contact: Contact) {
        startActivity(
            EditContactActivity.getIntent(
                this,
                contact.id,
                contact.name,
                contact.contact,
                contact.contactType
            )
        )
    }
}