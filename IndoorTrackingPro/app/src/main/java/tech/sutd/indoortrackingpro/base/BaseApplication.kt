package tech.sutd.indoortrackingpro.base

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import tech.sutd.indoortrackingpro.utils.appId
import tech.sutd.indoortrackingpro.utils.realmDb
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {

    private val TAG = "BaseApplication"

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        RealmLog.setLevel(LogLevel.ALL)


//        val app = App(AppConfiguration.Builder(appId)
//            .build())
//        val credentials = Credentials.anonymous()
//        app.loginAsync(credentials) {
//            if (it.isSuccess) {
//                Log.v(TAG, "Successfully authenticated anonymously.")
//                val user = app.currentUser()
//                val config = SyncConfiguration.Builder(user, realmDb)
//                    .allowQueriesOnUiThread(true)
//                    .allowWritesOnUiThread(true)
//                    .build()
//
//                val realm = Realm.getInstance(config)
//            } else {
//                Log.e(TAG, it.error.toString())
//            }
//        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}