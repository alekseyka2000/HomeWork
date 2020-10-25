package com.example.fourhw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.rbg
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.emailEditText
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.phoneEditText
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.radio1
import kotlinx.android.synthetic.main.activity_add_person_to_telefone_directory.radio2

class AddPersonToTelephoneDirectoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_person_to_telefone_directory)

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
}