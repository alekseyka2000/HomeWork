package com.example.sixth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CellClickListener {

    private lateinit var binding: ActivityMainBinding
    val fileList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fileList.apply {
            adapter = FileListAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCellClickListener(name: String) {
        Toast.makeText(this, "file", Toast.LENGTH_SHORT).show()
    }

    inner class FileListAdapter : RecyclerView.Adapter<FileListAdapter.FileViewHolder>() {

        override fun getItemCount(): Int {
            return fileList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.file_item, parent, false)
            return FileViewHolder(view)
        }

        override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
            holder.bind(fileList[position])
            holder.itemView.setOnClickListener {
                onCellClickListener(fileList[position])
            }
        }

        inner class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            private val fileName = view.findViewById<TextView>(R.id.fileName)

            fun bind(file: String) {
                fileName.text = file
            }
        }
    }
}