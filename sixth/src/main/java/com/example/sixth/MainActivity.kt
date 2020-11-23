package com.example.sixth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixth.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), CellClickListener {

    private lateinit var binding: ActivityMainBinding
    private var externalStorageIsOn = false
    val fileAdapter = FileListAdapter()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fileList.apply {
            adapter = fileAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        getFileList()

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
                                createFileInStorage(fileName, this@MainActivity, externalStorageIsOn)
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "file error", Toast.LENGTH_SHORT).show()
                    }
                    getFileList()
                }
                .setNegativeButton(resources.getString(R.string.negative_button_text)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
        binding.settingsButton.setOnClickListener {
            startActivityForResult(
                SettingActivity.getIntent(this, externalStorageIsOn),
                requestCode
            )
        }
    }

    private fun getFileList() {
        try {
            if (externalStorageIsOn){
                CompletableFuture.supplyAsync {
                    StorageManager().getFileListFromExternal(this@MainActivity)
                }.thenApplyAsync { result ->
                    fileAdapter.fileList = result
                }.thenRunAsync(Runnable { }, mainExecutor)
            }else{
                CompletableFuture.supplyAsync {
                    StorageManager().getFileList(this@MainActivity)
                }.thenApplyAsync { result ->
                    fileAdapter.fileList = result
                }.thenRunAsync(Runnable { }, mainExecutor)
            }
            binding.fileList.adapter?.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "file error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            requestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        externalStorageIsOn = it.getBooleanExtra("Result", false)
                    }
                    getFileList()
                } else Toast.makeText(this, "You didn't get setting", Toast.LENGTH_SHORT).show()
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onCellClickListener(name: String) {
        startActivity(FileTextActivity.getIntent(this, name, externalStorageIsOn))
    }

    inner class FileListAdapter : RecyclerView.Adapter<FileListAdapter.FileViewHolder>() {

        var fileList : List<String> by Delegates.observable(emptyList()){
                _, oldValue, newValue ->
            notifyChanges(oldValue, newValue)
        }

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

        private fun notifyChanges(oldList: List<String>, newList: List<String>) {
            val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition] == newList[newItemPosition]
                }
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition] == newList[newItemPosition]
                }
                override fun getOldListSize() = oldList.size
                override fun getNewListSize() = newList.size
            })
            diff.dispatchUpdatesTo(this)
        }
    }

    companion object {
        val requestCode = 1000
    }
}