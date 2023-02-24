package respo.app.nost.repositories

import android.util.Log
import kotlinx.coroutines.flow.flow
import respo.app.nost.R
import respo.app.nost.utils.RemoteConfig.configSettings
import respo.app.nost.utils.RemoteConfig.remoteConfig

class NewsRepository {
    fun getRemoteConfig() =
        getRemoteUrlResult()


    private fun getRemoteUrlResult() = flow {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        remoteConfig.activate()
        Log.d("Ray", remoteConfig.getString("url"))
        emit(remoteConfig.getString("url"))
    }
}