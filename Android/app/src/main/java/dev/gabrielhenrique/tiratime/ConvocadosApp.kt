package dev.gabrielhenrique.tiratime

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class ConvocadosApp: Application() {
    override fun onCreate() {
        println("Application onCreate")
        super.onCreate()

        // TODO: Verify why when the cache/storage is clear the fetch is not being made again
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate().addOnSuccessListener {
            println("fetchAndActivate success: $it")
        }.addOnFailureListener {
            println("fetchAndActivate failure")
            it.printStackTrace()
        }
    }
}