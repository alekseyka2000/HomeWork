package com.example.eight

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CellClickListener {

    private val contactAdapter = RecyclerAdapter(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TelephoneDirectory.openDB(this, contactAdapter, this.recyclerView)

        buttonAdd.setOnClickListener {
            startActivity(AddContactActivity.getIntent(this))
        }

        recyclerView.apply {
            layoutManager = if (resources.configuration.orientation == ORIENTATION_PORTRAIT)
                LinearLayoutManager(this@MainActivity)
            else GridLayoutManager(this@MainActivity, 2)
            adapter = contactAdapter
        }

        searchText.doAfterTextChanged {
            val subtext = it.toString()
            contactAdapter.listContacts = TelephoneDirectory.personList.filter { list ->
                list.name.contains(subtext, true)
                    .or(list.contact.contains(subtext, true))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        searchText.text = null
    }

    override fun onCellClickListener(id: String) {
        startActivity(EditContactActivity.getIntent(this, id))
    }
}