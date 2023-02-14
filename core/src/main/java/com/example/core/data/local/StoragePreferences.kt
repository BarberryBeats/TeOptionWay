package com.example.core.data.local

import android.content.Context

class StoragePreferences(context: Context) : BasePrefs(context) {

    override val prefFileName: String
        get() = "com.example.teoptionway.core.data.local.prefs"

    var url: String? by PrefDelegate(sharedPreference, Keys.URL, "")

    object Keys {
        const val URL = "URL"
    }
}