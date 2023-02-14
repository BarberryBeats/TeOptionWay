package com.example.teoptionway.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teoptionway.R
import com.example.teoptionway.utils.RemoteConfig.configSettings
import com.example.teoptionway.utils.RemoteConfig.remoteConfig

class NewsRepository {

    private val data = MutableLiveData<String>()

    fun getRemoteConfig(): LiveData<String> {

        data.value = ""

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    data.value = remoteConfig.getString("url")
                }
            }
        Log.d("Ray", data.value.toString())

        return data
    }


}