package com.example.sixth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixth.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity(), CellClickListener {

    private lateinit var binding: ActivityMainBinding
    var fileList = listOf<String>()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fileList.apply {
            adapter = FileListAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        try {
            CompletableFuture.supplyAsync {
                StorageManager().getFileList(this@MainActivity)
            }.thenApplyAsync { result ->
                fileList = result
                binding.fileList.adapter?.notifyDataSetChanged()
            }.thenRunAsync(Runnable { }, mainExecutor)
        } catch (e: Exception) {
            Toast.makeText(this, "file error", Toast.LENGTH_SHORT).show()
        }

        binding.addFileButton.setOnClickListener {
            MaterialAlertDialogBuilder(this@MainActivity, R.style.AppTheme)
                .setTitle(resources.getString(R.string.add_file))
                .setMessage(resources.getString(R.string.add_file))
                .setView(this.layoutInflater.inflate(R.layout.dialog_edittext, null))
                .setPositiveButton(resources.getString(R.string.positive_button_text)) { dialog, _ ->
                    try {
                        CompletableFuture.supplyAsync {
                            StorageManager().run {
                                val et =
                                    (dialog as? AlertDialog)?.findViewById<EditText>(R.id.fileName)
                                val fileName = et?.text.toString()
                                createFileInInternalStorage(fileName, this@MainActivity)
                                getFileList(this@MainActivity)
                            }
                        }.thenApplyAsync { result ->
                            fileList = result
                            binding.fileList.adapter?.notifyDataSetChanged()
                        }
                            .thenRunAsync(Runnable { }, mainExecutor)
                    } catch (e: Exception) {
                        Toast.makeText(this, "file error", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton(resources.getString(R.string.negative_button_text)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    override fun onCellClickListener(name: String) {
        startActivity(FileTextActivity.getIntent(this, name))
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