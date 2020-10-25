package com.example.fourhw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.buttonAdd

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        buttonAdd.setOnClickListener{
            startActivity(Intent(this,AddPersonToTelephoneDirectoryActivity::class.java))
        }
    }
}