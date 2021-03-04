package tech.sutd.indoortrackingpro.di.wifisearch

import android.net.wifi.WifiManager
import androidx.recyclerview.widget.DefaultItemAnimator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import tech.sutd.indoortrackingpro.adapter.WifiSearchAdapter
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver

@InstallIn(ActivityComponent::class)
@Module
object WifiSearchModule {

    @ActivityScoped
    @Provides
    fun provideWifiSearchAdapter() = WifiSearchAdapter()

    @ActivityScoped
    @Provides
    fun provideDefaultItemAnimator() = DefaultItemAnimator()

    @ActivityScoped
    @Provides
    fun provideWifiSearchReceiver(
        wifiManager: WifiManager,
        adapter: WifiSearchAdapter
    ): WifiSearchReceiver = WifiSearchReceiver(wifiManager, adapter)
}