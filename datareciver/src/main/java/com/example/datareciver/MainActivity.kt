package com.example.datareciver

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eleven.view.RecyclerAdapter

class MainActivity : AppCompatActivity() {

    private val listAdapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                LinearLayoutManager(this@MainActivity)
            else GridLayoutManager(this@MainActivity, 2)
            adapter = listAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        getContacts()
    }

    private fun getContacts() {
        val cursor = contentResolver.query(
            Uri.parse("content://com.example.eleven/data/data/"), null, null, null, null
        )

        if (cursor != null) {
            val newContactList = mutableListOf<Contact>()
            while (cursor.moveToNext()) {
                newContactList.add(
                    Contact(
                        cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("person_name")),
                        cursor.getString(cursor.getColumnIndex("contact_type")) != "0",
                        cursor.getString(cursor.getColumnIndex("contact"))
                    )
                )
            }
            listAdapter.listContacts = newContactList
            cursor.close()
        }
    }
}