package com.example.sixth

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.CompletableFuture

class StorageManager {

    fun getFileList(context: Context): List<String> {
        val list = mutableListOf<String>()
        CompletableFuture.runAsync {
            context.dataDir.listFiles()?.forEach { list.add(it.name) }
        }
        return list
    }

    fun getFileBody(context: Context, name: String): String {
        var textFile = ""
        FileInputStream(File(context.dataDir, name)).bufferedReader()
            .use { textFile = it.readText() }
        return textFile
    }

    fun setFileBody(context: Context, name: String, text: String) {
        FileOutputStream(File(context.dataDir, name)).writer().use { it.append(text) }
    }

    fun createFileInInternalStorage(name: String, context: Context) {
        File(context.dataDir, "$name.txt").createNewFile()
    }

    fun createFileInExternalStorage(name: String, context: Context) {
        File(context.getExternalFilesDir(null), "$name.txt")
    }
}