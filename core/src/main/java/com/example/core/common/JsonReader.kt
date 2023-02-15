package com.example.core.common

import android.content.Context
import android.util.Log
import java.io.IOException

class JsonReader {
    fun getJsonDataFromAsset(context: Context, jsonFile: String): String {
        var jsonString = ""
        try {
            jsonString = context.assets.open(jsonFile).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return jsonString
    }
}