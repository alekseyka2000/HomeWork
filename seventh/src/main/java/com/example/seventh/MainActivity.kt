package com.example.seventh

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.buttonAdd
import kotlinx.android.synthetic.main.activity_main.emptyListText
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.searchText

class MainActivity : AppCompatActivity(), CellClickListener {

    private val contactAdapter = RecyclerAdapter(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd.setOnClickListener {
            startActivity(AddContactActivity.getIntent(this))
        }

        if (TelephoneDirectory.personList.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            emptyListText.visibility = View.INVISIBLE
            recyclerView.apply {
                layoutManager = if (resources.configuration.orientation == ORIENTATION_PORTRAIT)
                    LinearLayoutManager(this@MainActivity)
                else GridLayoutManager(this@MainActivity, 2)
                adapter = contactAdapter
            }
            contactAdapter.listContacts = TelephoneDirectory.personList
        } else {
            recyclerView.visibility = View.INVISIBLE
            emptyListText.visibility = View.VISIBLE
        }

        searchText.doAfterTextChanged {
            val subtext = it.toString()
            val searchList = TelephoneDirectory.personList.filter { list ->
                list.name.contains(subtext, true)
                    .or(list.contact.contains(subtext, true))
            }
            contactAdapter.listContacts = searchList
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