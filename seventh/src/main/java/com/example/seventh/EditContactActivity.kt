package com.example.seventh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.seventh.DB.Contact
import kotlinx.android.synthetic.main.activity_edit_contact.buttonRemove
import kotlinx.android.synthetic.main.activity_edit_contact.contactEditText
import kotlinx.android.synthetic.main.activity_edit_contact.editTextTextPersonName
import kotlinx.android.synthetic.main.activity_edit_contact.toolBar

class EditContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        val selectedContact = TelephoneDirectory.findContact(intent)
        editTextTextPersonName.setText(selectedContact.name, TextView.BufferType.EDITABLE)
        contactEditText.setText(selectedContact.contact, TextView.BufferType.EDITABLE)

        setSupportActionBar(toolBar as Toolbar)
        supportActionBar?.apply {
            title = resources.getString(R.string.title_edit_contact)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        (toolBar as Toolbar).setNavigationOnClickListener {
            if (editTextTextPersonName.text.toString().isNotEmpty()) {
                if (contactEditText.text.toString().isNotEmpty()) {
                    val newContact = Contact(id = selectedContact.id,
                        name = editTextTextPersonName.text.toString(),
                        contactType = selectedContact.contactType,
                        contact = contactEditText.text.toString())
                    TelephoneDirectory.editContact(selectedContact, newContact)
                    Toast.makeText(this,
                        resources.getString(R.string.contact_edited),
                        Toast.LENGTH_LONG).show()
                } else Toast.makeText(this,
                    resources.getString(R.string.enter_contact),
                    Toast.LENGTH_LONG).show()
            } else Toast.makeText(this, resources.getString(R.string.enter_name), Toast.LENGTH_LONG)
                .show()
            finish()
        }

        buttonRemove.setOnClickListener {
            TelephoneDirectory.deleteContact(intent)
            Toast.makeText(this, resources.getString(R.string.contact_deleted), Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

    companion object {
        private const val ID_KEY = "ID"

        @JvmStatic
        fun getIntent(context: Context, extraId: String) =
            Intent(context, EditContactActivity::class.java)
            .putExtra(ID_KEY, extraId)
    }
}