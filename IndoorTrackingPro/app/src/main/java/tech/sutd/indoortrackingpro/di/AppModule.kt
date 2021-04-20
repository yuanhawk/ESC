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
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration
import io.realm.mongodb.sync.SyncSession
import tech.sutd.indoortrackingpro.core.TrackingAlgo
import tech.sutd.indoortrackingpro.data.datastore.Preferences
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.utils.appId
import tech.sutd.indoortrackingpro.utils.realmDb
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

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
    fun provideApp(): App =
        App(AppConfiguration.Builder(appId)
            .appName("IndoorTrackingPro")
            .requestTimeout(30, TimeUnit.SECONDS)
            .build())

    @Singleton
    @Provides
    fun provideCredentials(): Credentials =
        Credentials.anonymous()

    @Singleton
    @Provides
    fun provideSyncConfiguration(
        app: App
    ): SyncConfiguration {
        val syncAccount = SyncConfiguration.Builder(app.currentUser(), "_partition")
            .errorHandler { _, error ->
                Log.e("REALM", "error = " + error.message)
            }
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
            .inMemory()
            .build()

        syncAccount.shouldDeleteRealmIfMigrationNeeded()
        return syncAccount
    }

    @Singleton
    @Provides
    fun provideRealmConfiguration(): RealmConfiguration =
        RealmConfiguration.Builder()
            .name("default_realm")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
            .inMemory()
            .build()

    @Singleton
    @Provides
    fun provideRealmInstance(
        syncConfig: SyncConfiguration
    ): Realm =
        Realm.getInstance(syncConfig)

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