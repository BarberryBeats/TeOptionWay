package respo.app.nost.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfig {

    val remoteConfig: FirebaseRemoteConfig by lazy {
        FirebaseRemoteConfig.getInstance()
    }
   val configSettings: FirebaseRemoteConfigSettings by lazy {
       remoteConfigSettings {
           minimumFetchIntervalInSeconds = 1
       }
   }
}