package com.example.fourhw

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fourhw.observer.TelephoneDirectory
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.buttonAdd
import kotlinx.android.synthetic.main.activity_main.emptyListText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAdd.setOnClickListener {
            startActivity(Intent(this, AddPersonToTelephoneDirectoryActivity::class.java))
        }
        setRecyclerView()
    }

    private fun setRecyclerView (){
        if (TelephoneDirectory.personList.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            emptyListText.visibility = View.INVISIBLE
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = RecycleAdapter(TelephoneDirectory.personList)
            }
        }else{
            recyclerView.visibility = View.INVISIBLE
            emptyListText.visibility = View.VISIBLE
        }
    }
}