package com.example.sixth

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class StorageManager {

    fun getFileList(context: Context): List<String> {
        val list = mutableListOf<String>()
        context.dataDir.listFiles()?.forEach { list.add(it.name) }
        return list
    }

    fun getFileListFromExternal(context: Context): List<String> {
        val list = mutableListOf<String>()
        context.getExternalFilesDir(context.packageName)?.listFiles()?.forEach { list.add(it.name) }
        return list
    }

    fun getFileBody(context: Context, name: String): String {
        var textFile = ""
        FileInputStream(File(context.dataDir, name))
            .bufferedReader()
            .use { textFile = it.readText() }
        return textFile
    }

    fun getExternalFileBody(context: Context, name: String): String {
        var textFile = ""
        FileInputStream(File(context.getExternalFilesDir(context.packageName), name))
            .bufferedReader()
            .use { textFile = it.readText() }
        return textFile
    }

    fun setFileBody(context: Context, name: String, text: String) {
        FileOutputStream(File(context.dataDir, name)).writer().use { it.append(text) }
    }

    fun setExternalFileBody(context: Context, name: String, text: String) {
        FileOutputStream(File(context.getExternalFilesDir(context.packageName), name)).writer()
            .use { it.append(text) }
    }

    fun createFileInStorage(name: String, context: Context, storage: Boolean) {
        if (storage) File(
            context.getExternalFilesDir(context.packageName),
            "$name.txt"
        ).createNewFile()
        else File(context.dataDir, "$name.txt").createNewFile()
    }
}