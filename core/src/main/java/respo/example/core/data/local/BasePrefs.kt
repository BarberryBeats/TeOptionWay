package respo.example.core.data.local

import android.content.Context
import android.content.SharedPreferences

abstract class BasePrefs(private val context: Context) {

    abstract val prefFileName: String

    val sharedPreference: SharedPreferences by lazy {
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }



}