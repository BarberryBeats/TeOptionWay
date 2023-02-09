package com.example.teoptionway.utils

import android.content.Context
import java.io.IOException

class JsonReader {
        fun getJsonDataFromAsset(context: Context, jsonFile: String): String {
            val jsonString: String
            try {
                jsonString = context.assets.open(jsonFile).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return ioException.message.toString()
            }
            return jsonString
        }
}