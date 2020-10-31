package com.example.fourhw

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd.setOnClickListener {
            startActivity(Intent(this,
                AddContact::class.java))
        }

        searchText.doAfterTextChanged {
            val subtext = it.toString()
            val searchList = TelephoneDirectory.personList.filter { list ->
                list.name.contains(subtext, true)
                    .or(list.contact.contains(subtext, true))
            }
            setRecyclerView(searchList, this)
        }
    }

    override fun onStart() {
        super.onStart()
        setRecyclerView(TelephoneDirectory.personList, this)
        searchText.text = null
    }

    override fun onCellClickListener(id: String) {
        val newIntent = Intent(this, EditContact::class.java)
        newIntent.putExtra("ID", id)
        startActivity(newIntent)
    }

    private fun setRecyclerView(listContact: List<Person>, activity: MainActivity) {
        if (TelephoneDirectory.personList.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            emptyListText.visibility = View.INVISIBLE
            recyclerView.apply {
                layoutManager = if (resources.configuration.orientation == ORIENTATION_PORTRAIT)
                    LinearLayoutManager(activity)
                else GridLayoutManager(activity, 2)
                adapter = RecyclerAdapter(listContact, activity)
            }
        } else {
            recyclerView.visibility = View.INVISIBLE
            emptyListText.visibility = View.VISIBLE
        }
    }
}