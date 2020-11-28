package com.example.seventh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.seventh.DB.Contact
import kotlinx.android.synthetic.main.activity_add_contact.contactEditText
import kotlinx.android.synthetic.main.activity_add_contact.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_add_contact.rbg
import kotlinx.android.synthetic.main.activity_add_contact.toolBar
import java.util.UUID

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        setSupportActionBar(toolBar as Toolbar)
        supportActionBar?.apply {
            title = resources.getString(R.string.title_add_contact)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        (toolBar as Toolbar).setNavigationOnClickListener {
            finish()
        }

        rbg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio1 -> {
                    contactEditText.apply {
                        hint = resources.getString(R.string.phone_number)
                        inputType = InputType.TYPE_CLASS_PHONE
                    }
                }
                R.id.radio2 -> {
                    contactEditText.apply {
                        hint = resources.getString(R.string.email)
                        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    }
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
            if (editTextTextPersonName.text.toString().isNotEmpty()) {
                if (contactEditText.text.toString().isNotEmpty()) {
                    TelephoneDirectory.addContact(Contact(UUID.randomUUID().toString(),
                        editTextTextPersonName.text.toString(),
                        false,
                        contactEditText.text.toString()))
                    editTextTextPersonName.text = null
                    contactEditText.text = null
                    Toast.makeText(this,
                        resources.getString(R.string.contact_added),
                        Toast.LENGTH_LONG).show()
                } else Toast.makeText(this,
                    resources.getString(R.string.enter_contact),
                    Toast.LENGTH_LONG).show()
            } else Toast.makeText(this, resources.getString(R.string.enter_name), Toast.LENGTH_LONG)
                .show()
        }
        return true
    }

    companion object{
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, AddContactActivity::class.java)
    }
}