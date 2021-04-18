package tech.sutd.indoortrackingpro.di.wifisearch

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Handler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import io.realm.Realm
import io.realm.RealmConfiguration
import tech.sutd.indoortrackingpro.data.AddMappingPointReceiver
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.data.WifiWrapper
import tech.sutd.indoortrackingpro.databinding.AddMappingBinding
import tech.sutd.indoortrackingpro.ui.adapter.WifiListAdapter

@InstallIn(FragmentComponent::class)
@Module
object WifiSearchModule {

    @FragmentScoped
    @Provides
    fun provideWifiSearchReceiver(
        wifiManager: WifiManager,
        adapter: WifiListAdapter
    ): WifiSearchReceiver = WifiSearchReceiver(wifiManager, adapter)

    @FragmentScoped
    @Provides
    fun provideAddMappingPointReceiver(
        @ApplicationContext context: Context,
        handler: Handler,
        realm: Realm,
        wifiWrapper: WifiWrapper
    ): AddMappingPointReceiver = AddMappingPointReceiver(context, handler, realm, wifiWrapper)
}