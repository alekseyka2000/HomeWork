package com.example.fourhw

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_add_contact.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_add_contact.emailEditText
import kotlinx.android.synthetic.main.activity_add_contact.phoneEditText
import kotlinx.android.synthetic.main.activity_add_contact.radio1
import kotlinx.android.synthetic.main.activity_add_contact.radio2
import kotlinx.android.synthetic.main.activity_add_contact.rbg
import kotlinx.android.synthetic.main.activity_add_contact.toolBar
import java.util.UUID

class AddContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        setSupportActionBar(toolBar as Toolbar?)
        supportActionBar?.title = "Add contact"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        (toolBar as Toolbar?)?.setNavigationOnClickListener {
            finish()
        }

        rbg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio1 -> {
                    radio1.isChecked = true
                    radio2.isChecked = false
                    phoneEditText.visibility = View.VISIBLE
                    emailEditText.visibility = View.INVISIBLE
                }
                R.id.radio2 -> {
                    radio1.isChecked = false
                    radio2.isChecked = true
                    phoneEditText.visibility = View.INVISIBLE
                    emailEditText.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.check_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_check) {
            if (radio1.isChecked) {
                if (editTextTextPersonName.text.toString() != "") {
                    if (phoneEditText.text.toString() != "") {
                        TelephoneDirectory.addContact(Person(UUID.randomUUID().toString(),
                            editTextTextPersonName.text.toString(),
                            false,
                            phoneEditText.text.toString()))
                        editTextTextPersonName.text = null
                        emailEditText.text = null
                        phoneEditText.text = null
                        Toast.makeText(this, "Contact added", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(this, "Enter phone", Toast.LENGTH_LONG).show()
                } else Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show()
            }
            if (radio2.isChecked) {
                if (editTextTextPersonName.text.toString() != "") {
                    if (emailEditText.text.toString() != "") {
                        TelephoneDirectory.addContact(
                            Person(UUID.randomUUID().toString(),
                                editTextTextPersonName.text.toString(),
                                true,
                                emailEditText.text.toString()))
                        editTextTextPersonName.text = null
                        emailEditText.text = null
                        phoneEditText.text = null
                        Toast.makeText(this, "Contact added", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show()
                } else Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }
}