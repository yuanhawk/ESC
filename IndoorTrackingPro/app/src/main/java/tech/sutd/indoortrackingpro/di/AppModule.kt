package tech.sutd.indoortrackingpro.di

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.*
import io.realm.mongodb.sync.ClientResetRequiredError
import io.realm.mongodb.sync.SyncConfiguration
import io.realm.mongodb.sync.SyncSession
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.core.TrackingAlgo
import tech.sutd.indoortrackingpro.data.datastore.Preferences
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.utils.appId
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    private const val TAG = "AppModule"

    @Singleton
    @Provides
    fun provideWifiManager(@ApplicationContext context: Context): WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Singleton
    @Provides
    fun provideWorkerManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    @Singleton
    @Provides
    fun provideRealmConfiguration(): RealmConfiguration =
        RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()

    @Singleton
    @Provides
    fun provideApp(): App {
        return App(
            AppConfiguration.Builder(appId)
                .build()
        )
    }

    @Singleton
    @Provides
    fun provideCredentials(): Credentials = Credentials.anonymous()

        @Singleton
        @Provides
        fun provideSyncConfiguration(
            cred: Credentials,
            app: App,
        ): SyncConfiguration {

            app.loginAsync(cred) {
                if (it.isSuccess) {
                    Log.v("AUTH", "Successfully authenticated anonymously.")
                } else {
                    Log.e("AUTH", it.error.toString())
                }
            }

            return SyncConfiguration.Builder(app.currentUser(), ObjectId())
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .errorHandler { _, error ->
                    if (error.errorCode == ErrorCode.CLIENT_RESET) {
                        val clientResetError = error as ClientResetRequiredError
                        Log.e(TAG, "Received a ClientResetRequiredError",)
                        clientResetError.executeClientReset()
                        Log.e(TAG, "Reset client. Backup file path: ${clientResetError.backupFile}")
                    }
                }
                .build()
        }

        @Singleton
        @Provides
        fun provideRealmInstance(
            syncConfig: SyncConfiguration
        ): Realm =
            Realm.getInstance(syncConfig)

        @Singleton
        @Provides
        fun provideSyncSession(
            app: App,
            config: SyncConfiguration
        ): SyncSession = app.sync.getOrCreateSession(config)

        @Singleton
        @Provides
        fun provideTrackingHelper(): AlgoHelper = TrackingAlgo()

        @Singleton
        @Provides
        fun provideListScanResult(): MediatorLiveData<List<ScanResult>> =
            MediatorLiveData()

        @Singleton
        @Provides
        fun provideListApt(): MediatorLiveData<List<Account_mAccessPoints>> =
            MediatorLiveData()

        @Singleton
        @Provides
        fun providePreferences(@ApplicationContext context: Context): Preferences = Preferences(context)
}