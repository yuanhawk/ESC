package tech.sutd.indoortrackingpro.di.wifisearch

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import tech.sutd.indoortrackingpro.ui.wifi.WifiListAdapter

@InstallIn(FragmentComponent::class)
@Module
object WifiSearchModule {

    @FragmentScoped
    @Provides
    fun provideWifiListAdapter(): WifiListAdapter = WifiListAdapter()
}