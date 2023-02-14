package com.example.teoptionway.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teoptionway.R
import com.example.teoptionway.utils.RemoteConfig.configSettings
import com.example.teoptionway.utils.RemoteConfig.remoteConfig
import kotlinx.coroutines.*

class NewsRepository {



   fun getRemoteConfig(): MutableLiveData<String> {

        val data = MutableLiveData<String>()
       data.value = ""

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch{
                            data.value = remoteConfig.getString("url")

                    }
                    Log.d("RAY", "Data ${remoteConfig.getString("url")}")
                }
            }
        Log.d("Ray", data.value.toString())

        return data
    }


}