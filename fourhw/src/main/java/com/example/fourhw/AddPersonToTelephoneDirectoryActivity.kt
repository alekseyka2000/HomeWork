package com.example.fourhw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.fourhw.observer.Person
import com.example.fourhw.observer.TelephoneDirectory
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.rbg
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.emailEditText
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.phoneEditText
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.radio1
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.radio2
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.toolBar
import java.util.UUID

class AddPersonToTelephoneDirectoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_person_to_telefone_directory)

        setSupportActionBar(toolBar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        (toolBar as Toolbar?)?.setNavigationOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
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
                        TelephoneDirectory.person = Person(UUID.randomUUID().toString(),
                            editTextTextPersonName.text.toString(),
                            false,
                            phoneEditText.text.toString())
                        editTextTextPersonName.text = null
                        emailEditText.text = null
                        phoneEditText.text = null
                        Toast.makeText(this, "Contact add", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(this, "Enter phone", Toast.LENGTH_LONG).show()
                } else Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show()
            }
            if (radio2.isChecked) {
                if (editTextTextPersonName.text.toString() != "") {
                    if (emailEditText.text.toString() != "") {
                        TelephoneDirectory.person =
                            Person(UUID.randomUUID().toString(),
                                editTextTextPersonName.text.toString(),
                                true,
                                emailEditText.text.toString())
                        editTextTextPersonName.text = null
                        emailEditText.text = null
                        phoneEditText.text = null
                        Toast.makeText(this, "Contact add", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show()
                } else Toast.makeText(this, "Enter name", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }
}