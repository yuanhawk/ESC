package tech.sutd.indoortrackingpro.base

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.utils.Constants
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}