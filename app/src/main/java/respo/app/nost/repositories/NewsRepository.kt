package respo.app.nost.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import respo.app.nost.R
import respo.app.nost.utils.RemoteConfig.configSettings
import respo.app.nost.utils.RemoteConfig.remoteConfig

class NewsRepository {


    fun getRemoteUrlResult(): LiveData<String> {
        val liveData = MutableLiveData<String>()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        try {
            remoteConfig.fetchAndActivate().addOnCompleteListener {
                liveData.value = remoteConfig.getString("url")
            }.addOnFailureListener {
            }
        } catch(e: Exception){

        }


        return liveData
    }
}
