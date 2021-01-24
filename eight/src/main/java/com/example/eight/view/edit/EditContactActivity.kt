package com.example.eight.view.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.eight.R
import com.example.eight.model.db.Contact
import kotlinx.android.synthetic.main.activity_edit_contact.*
import org.koin.android.viewmodel.ext.android.viewModel

class EditContactActivity : AppCompatActivity() {

    private val editContactViewModel: EditContactViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        val selectedContactId = intent.getStringExtra(ID_KEY)
        val selectedContactName = intent.getStringExtra(NAME_KEY)
        val selectedContact = intent.getStringExtra(CONTACT_KEY)
        val selectedContactType = intent.getBooleanExtra(TYPE_CONTACT_KEY, false)
        editTextTextPersonName.setText(selectedContactName, TextView.BufferType.EDITABLE)
        contactEditText.setText(selectedContact, TextView.BufferType.EDITABLE)

        setSupportActionBar(toolBar as Toolbar)
        supportActionBar?.apply {
            title = resources.getString(R.string.title_edit_contact)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        (toolBar as Toolbar).setNavigationOnClickListener {
            if (editTextTextPersonName.text.toString().isNotEmpty()) {
                if (contactEditText.text.toString().isNotEmpty()) {
                    selectedContactId?.let {
                        val newContact = Contact(
                            id = selectedContactId,
                            name = editTextTextPersonName.text.toString(),
                            contactType = selectedContactType,
                            contact = contactEditText.text.toString()
                        )
                        editContactViewModel.editContactToList(newContact)
                        Toast.makeText(
                            this,
                            resources.getString(R.string.contact_edited),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else Toast.makeText(
                    this,
                    resources.getString(R.string.enter_contact),
                    Toast.LENGTH_LONG
                ).show()
            } else Toast.makeText(this, resources.getString(R.string.enter_name), Toast.LENGTH_LONG)
                .show()
            finish()
        }

        buttonRemove.setOnClickListener {
            selectedContactId?.let {
                editContactViewModel.deleteContact(selectedContactId)
                Toast.makeText(
                    this,
                    resources.getString(R.string.contact_deleted),
                    Toast.LENGTH_LONG
                )
                    .show()
                finish()
            }
        }
    }

    companion object {
        private const val ID_KEY = "ID"
        private const val NAME_KEY = "NAME_KEY"
        private const val CONTACT_KEY = "CONTACT_KEY"
        private const val TYPE_CONTACT_KEY = "TYPE_CONTACT_KEY"

        @JvmStatic
        fun getIntent(
            context: Context,
            extraId: String,
            extraName: String,
            extraContact: String,
            extraContactType: Boolean
        ): Intent {
            return Intent(context, EditContactActivity::class.java)
                .putExtra(ID_KEY, extraId)
                .putExtra(NAME_KEY, extraName)
                .putExtra(CONTACT_KEY, extraContact)
                .putExtra(TYPE_CONTACT_KEY, extraContactType)
        }
    }
}