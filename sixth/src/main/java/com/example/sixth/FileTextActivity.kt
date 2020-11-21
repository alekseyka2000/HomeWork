package com.example.sixth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sixth.databinding.ActivityFileTextBinding
import java.util.concurrent.CompletableFuture

class FileTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_text)
        binding = ActivityFileTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nameFile = intent.getStringExtra(KEY)!!
        try {
            CompletableFuture.supplyAsync {
                StorageManager().getFileBody(this@FileTextActivity, nameFile)
            }.thenApplyAsync { res ->
                binding.fileText.setText(res, TextView.BufferType.EDITABLE)
            }.thenRunAsync(Runnable {}, mainExecutor)
        } catch (e: Exception) {
            Toast.makeText(this, "file error", Toast.LENGTH_SHORT).show()
        }

        binding.resumeButton.setOnClickListener {
            try {
                StorageManager().setFileBody(
                    this@FileTextActivity,
                    nameFile,
                    binding.fileText.text.toString()
                )
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "file error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val KEY = "FileName"

        @JvmStatic
        fun getIntent(context: Context, extra: String) =
            Intent(context, FileTextActivity::class.java)
                .apply {
                    putExtra(KEY, extra)
                }
    }
}