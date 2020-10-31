package com.example.fourhw

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_edit_contact.buttonRemove
import kotlinx.android.synthetic.main.activity_edit_contact.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_edit_contact.emailEditText
import kotlinx.android.synthetic.main.activity_edit_contact.phoneEditText
import kotlinx.android.synthetic.main.activity_edit_contact.toolBar

class EditContact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        val contact = TelephoneDirectory.personList.first { it.id == intent.getStringExtra("ID") }
        editTextTextPersonName.setText(contact.name, TextView.BufferType.EDITABLE)
        if (contact.typeContact) {
            phoneEditText.visibility = View.INVISIBLE
            emailEditText.visibility = View.VISIBLE
            emailEditText.setText(contact.contact, TextView.BufferType.EDITABLE)
        } else {
            phoneEditText.visibility = View.VISIBLE
            emailEditText.visibility = View.INVISIBLE
            phoneEditText.setText(contact.contact, TextView.BufferType.EDITABLE)
        }

        setSupportActionBar(toolBar as Toolbar?)
        supportActionBar?.title = "Edit Contact"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        (toolBar as Toolbar?)?.setNavigationOnClickListener {
            finish()
        }

        buttonRemove.setOnClickListener {
            val index =
                TelephoneDirectory.personList.indexOfFirst { it.id == intent.getStringExtra("ID") }
            if (editTextTextPersonName.text.toString() != "") {
                if (contact.typeContact) {
                    if (emailEditText.text.toString() != "") {
                        TelephoneDirectory.personList[index] = Person(contact.id,
                            editTextTextPersonName.text.toString(),
                            true, emailEditText.text.toString())
                        Toast.makeText(this, "Contact edited", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show()
                } else {
                    if (phoneEditText.text.toString() != "") {
                        TelephoneDirectory.personList[index] = Person(contact.id,
                            editTextTextPersonName.text.toString(),
                            true, phoneEditText.text.toString())
                        Toast.makeText(this, "Contact edited", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(this, "Enter phone", Toast.LENGTH_LONG).show()
                }
            } else Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show()
        }
    }
}