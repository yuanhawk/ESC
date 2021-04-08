package tech.sutd.indoortrackingpro.di.wifisearch

import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import io.realm.RealmConfiguration
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.ui.adapter.WifiListAdapter

@InstallIn(FragmentComponent::class)
@Module
object WifiSearchModule {

    @FragmentScoped
    @Provides
    fun provideWifiListAdapter(
        config: RealmConfiguration
    ): WifiListAdapter = WifiListAdapter(config)

    @FragmentScoped
    @Provides
    fun provideWifiSearchReceiver(
        wifiManager: WifiManager,
        adapter: WifiListAdapter
    ): WifiSearchReceiver = WifiSearchReceiver(wifiManager, adapter)
}