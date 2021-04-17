package tech.sutd.indoortrackingpro.base

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import tech.sutd.indoortrackingpro.utils.appId
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {

    private val TAG = "BaseApplication"

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val app = App(AppConfiguration.Builder(appId)
            .build())
        val credentials = Credentials.anonymous()
        var user: User? = null
        app.loginAsync(credentials) {
            if (it.isSuccess) {
                Log.v(TAG, "Successfully authenticated anonymously.")
                user = app.currentUser()
            } else {
                Log.e(TAG, it.error.toString())
            }
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}