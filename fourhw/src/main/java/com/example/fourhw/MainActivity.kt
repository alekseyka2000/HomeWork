package com.example.fourhw

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.buttonAdd
import kotlinx.android.synthetic.main.activity_main.emptyListText

class MainActivity : AppCompatActivity(), CellClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd.setOnClickListener {
            startActivity(Intent(this,
                AddContact::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        setRecyclerView(this)
    }

    override fun onCellClickListener(id: String) {
        val newIntent = Intent(this, EditContact::class.java)
        newIntent.putExtra("ID", id)
        startActivity(newIntent)
    }

    private fun setRecyclerView(activity: MainActivity) {
        if (TelephoneDirectory.personList.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            emptyListText.visibility = View.INVISIBLE
            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = RecyclerAdapter(TelephoneDirectory.personList, activity)
            }
        } else {
            recyclerView.visibility = View.INVISIBLE
            emptyListText.visibility = View.VISIBLE
        }
    }
}