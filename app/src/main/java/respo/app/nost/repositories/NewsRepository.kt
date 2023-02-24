package respo.app.nost.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.flow
import respo.app.nost.R
import respo.app.nost.utils.RemoteConfig.configSettings
import respo.app.nost.utils.RemoteConfig.remoteConfig

class NewsRepository {


     fun getRemoteUrlResult(): LiveData<String> {
         Log.d("Ray", "test")
        val urlLiveData =  MutableLiveData("asa")
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
         remoteConfig.activate()
        Log.d("Ray", remoteConfig.getString("url"))
          remoteConfig.fetch(1).addOnCompleteListener {
              if (it.isSuccessful){
                  Log.d("Ray", "test" + remoteConfig.getString("url"))
                urlLiveData.postValue(remoteConfig.getString("url"))
              }
          }.addOnFailureListener {
              Log.d("Ray", "failure")
          }
        return urlLiveData
          }
    }
