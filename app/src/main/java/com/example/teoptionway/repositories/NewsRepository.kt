package com.example.teoptionway.repositories

import com.example.teoptionway.R
import com.example.teoptionway.utils.RemoteConfig.configSettings
import com.example.teoptionway.utils.RemoteConfig.remoteConfig
import kotlinx.coroutines.flow.flow

class NewsRepository {
    fun getRemoteConfig() =
        getRemoteUrlResult()


    fun getRemoteUrlResult() = flow {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        remoteConfig.activate()
        emit(remoteConfig.getString("url"))
    }
}