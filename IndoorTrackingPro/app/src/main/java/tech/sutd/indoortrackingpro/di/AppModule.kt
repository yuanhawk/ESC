package tech.sutd.indoortrackingpro.di

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import tech.sutd.indoortrackingpro.core.TrackingAlgo
import tech.sutd.indoortrackingpro.data.datastore.Preferences
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideHandler() = Handler(Looper.getMainLooper())

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
    fun provideRealmInstance(
        realmConfig: RealmConfiguration
    ): Realm =
        Realm.getInstance(realmConfig)

    @Singleton
    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager =
        LinearLayoutManager(context)

    @Singleton
    @Provides
    fun provideDividerItemDecoration(@ApplicationContext context: Context) =
        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

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
    fun provideBundle(): Bundle = Bundle()

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): Preferences = Preferences(context)
}