package tech.sutd.indoortrackingpro.di.wifisearch

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.lifecycle.MediatorLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import io.realm.Realm
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.ui.wifi.WifiListAdapter

@InstallIn(FragmentComponent::class)
@Module
object WifiSearchModule {

    @FragmentScoped
    @Provides
    fun provideWifiListAdapter(
        realm: Realm
    ): WifiListAdapter = WifiListAdapter(realm)

    @FragmentScoped
    @Provides
    fun provideWifiSearchReceiver(
        wifiManager: WifiManager,
        adapter: WifiListAdapter
    ): WifiSearchReceiver = WifiSearchReceiver(wifiManager, adapter)
}